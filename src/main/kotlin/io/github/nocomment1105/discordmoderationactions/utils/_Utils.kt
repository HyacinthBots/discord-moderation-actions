/*
 * Copyright (c) 2022 NoComment1105 <nocomment1105@outlook.com>
 *
 * This file is part of discord-moderation-actions.
 *
 * Licensed under the MIT license. For more information,
 * please see the LICENSE file or https://mit-license.org/
 */

package io.github.nocomment1105.discordmoderationactions.utils

import com.kotlindiscord.kord.extensions.checks.guildFor
import com.kotlindiscord.kord.extensions.commands.application.slash.SlashCommandContext
import com.kotlindiscord.kord.extensions.utils.dm
import com.kotlindiscord.kord.extensions.utils.timeoutUntil
import dev.kord.core.behavior.channel.MessageChannelBehavior
import dev.kord.core.behavior.channel.createMessage
import dev.kord.core.behavior.edit
import dev.kord.core.behavior.getChannelOfOrNull
import dev.kord.core.entity.User
import dev.kord.core.entity.channel.GuildMessageChannel
import dev.kord.rest.builder.message.EmbedBuilder
import io.github.nocomment1105.discordmoderationactions.enums.DMOutcome

public suspend inline fun SlashCommandContext<*, *>.removeTimeout(removeTimeout: Boolean, user: User) {
	if (removeTimeout) {
		user.asMemberOrNull(guildFor(event)!!.id)?.edit { timeoutUntil = null }
	}
}

public suspend inline fun sendDm(sendDm: Boolean, user: User, dmEmbedBuilder: EmbedBuilder): DMOutcome =
	if (sendDm) {
		val dm = user.asUser().dm {
			embeds.add(dmEmbedBuilder)
		}

		if (dm == null) {
			DMOutcome.FAIL
		} else {
			DMOutcome.SUCCESS
		}
	} else {
		DMOutcome.NOT_SENT
	}

public suspend inline fun logPublicly(logPublicly: Boolean?, channel: MessageChannelBehavior, logEmbed: EmbedBuilder) {
	if (logPublicly != null && logPublicly) {
		channel.createMessage {
			embeds.add(logEmbed)
		}
	}
}

public suspend inline fun SlashCommandContext<*, *>.logPrivately(
	sendLog: Boolean,
	hasChannelPerms: Boolean?,
	channel: MessageChannelBehavior,
	actionEmbed: EmbedBuilder
): String? =
	if (sendLog && hasChannelPerms == true) {
		val message = guildFor(event)!!.getChannelOfOrNull<GuildMessageChannel>(channel.id)?.createMessage {
			embeds.add(actionEmbed)
		}
		if (message == null) "" else "success"
	} else {
		null
	}
