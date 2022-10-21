/*
 * Copyright (c) 2022 NoComment1105 <nocomment1105@outlook.com>
 *
 * This file is part of discord-moderation-actions.
 *
 * Licensed under the MIT license. For more information,
 * please see the LICENSE file or https://mit-license.org/
 */

@file:Suppress("DuplicatedCode")

package io.github.nocomment1105.discordmoderationactions.builder

import com.kotlindiscord.kord.extensions.commands.application.slash.SlashCommandContext
import com.kotlindiscord.kord.extensions.utils.toDuration
import dev.kord.core.behavior.ban
import io.github.nocomment1105.discordmoderationactions.builder.action.BanActionBuilder
import io.github.nocomment1105.discordmoderationactions.builder.action.KickActionBuilder
import io.github.nocomment1105.discordmoderationactions.builder.action.SoftBanActionBuilder
import io.github.nocomment1105.discordmoderationactions.utils.Result
import io.github.nocomment1105.discordmoderationactions.utils.logPrivately
import io.github.nocomment1105.discordmoderationactions.utils.logPublicly
import io.github.nocomment1105.discordmoderationactions.utils.removeTimeout
import io.github.nocomment1105.discordmoderationactions.utils.sendDm
import kotlinx.datetime.TimeZone

/**
 * DSL function for easily running a ban action.
 * This will ban the user, and carryout any extra tasks specified
 *
 * @param builder - Builder lambda used for setting up the ban action
 * @see BanActionBuilder
 */
public suspend inline fun SlashCommandContext<*, *>.ban(builder: BanActionBuilder.() -> Unit): Result {
	val action = BanActionBuilder()
	action.builder()

	action.dmOutcome = sendDm(action.sendDm, action.user, action.dmEmbedBuilder).message()

	removeTimeout(action.removeTimeout, action.user)

	action.user.asMember(guild!!.id).ban {
		reason = action.reason
		deleteMessageDuration = action.deleteMessageDuration.toDuration(TimeZone.UTC)
	}

	logPublicly(action.logPublicly, channel, action.publicActionEmbedBuilder)

	return when (
		logPrivately(
			action.sendActionLog,
			action.hasLogChannelPerms,
			action.loggingChannel,
			action.actionEmbedBuilder
		)
	) {
		"" -> Result("Well fuck")
		"success" -> Result("yey")
		else -> Result("No log sent")
	}
}

@Suppress("DuplicatedCode")
public suspend inline fun SlashCommandContext<*, *>.softban(builder: SoftBanActionBuilder.() -> Unit): Result {
	val action = SoftBanActionBuilder()
	action.builder()

	action.dmOutcome = sendDm(action.sendDm, action.user, action.dmEmbedBuilder).message()

	removeTimeout(action.removeTimeout, action.user)

	action.user.asMember(guild!!.id).ban {
		reason = action.reason
		deleteMessageDuration = action.deleteMessageDuration.toDuration(TimeZone.UTC)
	}

	logPublicly(action.logPublicly, channel, action.publicActionEmbedBuilder)

	return when (
		logPrivately(
			action.sendActionLog,
			action.hasLogChannelPerms,
			action.loggingChannel,
			action.actionEmbedBuilder
		)
	) {
		"" -> Result("Well fuck")
		"success" -> Result("yey")
		else -> Result("No log sent")
	}
}

public suspend inline fun SlashCommandContext<*, *>.kick(builder: KickActionBuilder.() -> Unit): Result {
	val action = KickActionBuilder()
	action.builder()

	action.dmOutcome = sendDm(action.sendDm, action.user, action.dmEmbedBuilder).message()

	removeTimeout(action.removeTimeout, action.user)

	action.user.asMember(guild!!.id).kick(action.reason)

	logPublicly(action.logPublicly, channel, action.publicActionEmbedBuilder)

	return when (
		logPrivately(
			action.sendActionLog,
			action.hasLogChannelPerms,
			action.loggingChannel,
			action.actionEmbedBuilder
		)
	) {
		"" -> Result("Well fuck")
		"success" -> Result("yey")
		else -> Result("No log sent")
	}
}
