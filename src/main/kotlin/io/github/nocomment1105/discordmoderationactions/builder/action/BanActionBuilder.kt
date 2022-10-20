/*
 * Copyright (c) 2022 NoComment1105 <nocomment1105@outlook.com>
 *
 * This file is part of discord-moderation-actions.
 *
 * Licensed under the MIT license. For more information,
 * please see the LICENSE file or https://mit-license.org/
 */

package io.github.nocomment1105.discordmoderationactions.builder.action

import com.kotlindiscord.kord.extensions.commands.application.slash.SlashCommandContext
import com.kotlindiscord.kord.extensions.utils.dm
import com.kotlindiscord.kord.extensions.utils.timeoutUntil
import com.kotlindiscord.kord.extensions.utils.toDuration
import dev.kord.core.behavior.ban
import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.behavior.edit
import dev.kord.core.behavior.getChannelOfOrNull
import dev.kord.core.entity.User
import dev.kord.core.entity.channel.GuildMessageChannel
import dev.kord.rest.builder.message.EmbedBuilder
import io.github.nocomment1105.discordmoderationactions.annotations.ActionBuilderDSL
import io.github.nocomment1105.discordmoderationactions.enums.DMOutcome
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.TimeZone

@ActionBuilderDSL
public open class BanActionBuilder : Action {
	/** Whether to send a DM about this action to the user. Default: true. */
	public open var sendDm: Boolean = true

	/** Whether to remove any outstanding timeout the user has applied to them before banning. Default: false. */
	public open var removeTimeout: Boolean = false

	/** Whether to send a message to the action log provided. Default: True. */
	public open var sendActionLog: Boolean = true

	/** The duration of past messages to delete for this user. */
	public open lateinit var deleteMessageDuration: DateTimePeriod

	/** The user to ban. */
	public override lateinit var user: User

	/** The outcome of DMing the user. */
	public override lateinit var dmOutcome: String

	/** The reason for the ban. */
	public override lateinit var reason: String

	/** THe logging channel to send the [actionEmbed] too. */
	public override lateinit var loggingChannel: GuildMessageChannel

	/** Whether to log the action publicly. I.E in the channel the command was run in. Default: false*/
	public override var logPublicly: Boolean? = null

	/** @suppress Builder that shouldn't be set directly by the user. */
	public override val dmEmbedBuilder: EmbedBuilder = EmbedBuilder()

	/** @suppress Builder that shouldn't be set directly by the user. */
	public override val actionEmbedBuilder: EmbedBuilder = EmbedBuilder()

	/** @suppress Builder that shouldn't be set directly by the user. */
	public override val publicActionEmbedBuilder: EmbedBuilder = EmbedBuilder()

	/**
	 * Whether the bot has permission the required permissions to use the logging channel. This should be evaluated
	 * by a check system from within the bot and passed into this variable.
	 */
	public override var hasLogChannelPerms: Boolean? = null

	/**
	 * DSL function used to configure the DM embed.
	 *
	 * @see EmbedBuilder
	 */
	public override fun dmEmbed(builder: EmbedBuilder.() -> Unit) {
		builder(dmEmbedBuilder)
	}

	/**
	 * DSL function used to configure the Action log embed.
	 *
	 * @see EmbedBuilder
	 */
	public override fun actionEmbed(builder: EmbedBuilder.() -> Unit) {
		builder(actionEmbedBuilder)
	}

	/**
	 * DSL function used to configure the DM embed.
	 *
	 * @see EmbedBuilder
	 */
	public override fun publicActionEmbed(builder: EmbedBuilder.() -> Unit) {
		builder(publicActionEmbedBuilder)
	}
}

/**
 * DSL function for easily running a ban action.
 * This will ban the user, and carryout any extra tasks specified
 *
 * @param builder - Builder lambda used for setting up the ban action
 * @see BanActionBuilder
 */
public suspend inline fun SlashCommandContext<*, *>.ban(builder: BanActionBuilder.() -> Unit) {
	val action = BanActionBuilder()
	action.builder()

	if (action.sendDm) {
		val dm = action.user.asUser().dm {
			action.dmEmbedBuilder
		}
		if (dm == null) {
			action.dmOutcome = DMOutcome.FAIL.message()
		} else {
			action.dmOutcome = DMOutcome.SUCCESS.message()
		}
	}

	if (action.removeTimeout) {
		action.user.asMemberOrNull(guild!!.id)?.edit { timeoutUntil = null }
	}

	action.user.asMember(guild!!.id).ban {
		reason = action.reason
		deleteMessageDuration = action.deleteMessageDuration.toDuration(TimeZone.UTC)
	}

	if (action.logPublicly != null && action.logPublicly == true) {
		channel.createEmbed {
			action.publicActionEmbedBuilder
		}
	}

	if (!action.sendActionLog && (action.hasLogChannelPerms == null || action.hasLogChannelPerms == true)) {
		guild!!.getChannelOfOrNull<GuildMessageChannel>(action.loggingChannel.id)?.createEmbed {
			action.actionEmbedBuilder
		}
	}
}
