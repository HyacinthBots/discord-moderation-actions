/*
 * Copyright (c) 2022 NoComment1105 <nocomment1105@outlook.com>
 *
 * This file is part of discord-moderation-actions.
 *
 * Licensed under the MIT license. For more information,
 * please see the LICENSE file or https://mit-license.org/
 */

package io.github.nocomment1105.discordmoderationactions.builder.action

import dev.kord.core.entity.User
import dev.kord.core.entity.channel.GuildMessageChannel
import dev.kord.rest.builder.message.EmbedBuilder
import io.github.nocomment1105.discordmoderationactions.annotations.ActionBuilderDSL
import kotlinx.datetime.DateTimePeriod

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
