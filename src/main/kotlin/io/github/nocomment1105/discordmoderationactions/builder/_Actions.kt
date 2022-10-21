/*
 * Copyright (c) 2022 NoComment1105 <nocomment1105@outlook.com>
 *
 * This file is part of discord-moderation-actions.
 *
 * Licensed under the MIT license. For more information,
 * please see the LICENSE file or https://mit-license.org/
 */

package io.github.nocomment1105.discordmoderationactions.builder

import com.kotlindiscord.kord.extensions.commands.application.slash.SlashCommandContext
import com.kotlindiscord.kord.extensions.utils.dm
import com.kotlindiscord.kord.extensions.utils.timeoutUntil
import com.kotlindiscord.kord.extensions.utils.toDuration
import dev.kord.core.behavior.ban
import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.behavior.edit
import dev.kord.core.behavior.getChannelOfOrNull
import dev.kord.core.entity.channel.GuildMessageChannel
import io.github.nocomment1105.discordmoderationactions.builder.action.BanActionBuilder
import io.github.nocomment1105.discordmoderationactions.builder.action.KickActionBuilder
import io.github.nocomment1105.discordmoderationactions.builder.action.SoftBanActionBuilder
import io.github.nocomment1105.discordmoderationactions.enums.DMOutcome
import kotlinx.datetime.TimeZone

/**
 * DSL function for easily running a ban action.
 * This will ban the user, and carryout any extra tasks specified
 *
 * @param builder - Builder lambda used for setting up the ban action
 * @see BanActionBuilder
 */
@Suppress("DuplicatedCode")
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

@Suppress("DuplicatedCode")
public suspend inline fun SlashCommandContext<*, *>.softban(builder: SoftBanActionBuilder.() -> Unit) {
	val action = SoftBanActionBuilder()
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

public suspend inline fun SlashCommandContext<*, *>.kick(builder: KickActionBuilder.() -> Unit) {
	val action = KickActionBuilder()
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

	action.user.asMember(guild!!.id).kick(action.reason)

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
