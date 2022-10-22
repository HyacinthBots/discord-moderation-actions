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
import com.kotlindiscord.kord.extensions.utils.timeoutUntil
import com.kotlindiscord.kord.extensions.utils.toDuration
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.UserBehavior
import dev.kord.core.behavior.ban
import dev.kord.core.behavior.edit
import dev.kord.core.entity.User
import io.github.nocomment1105.discordmoderationactions.builder.action.BanActionBuilder
import io.github.nocomment1105.discordmoderationactions.builder.action.KickActionBuilder
import io.github.nocomment1105.discordmoderationactions.builder.action.SoftBanActionBuilder
import io.github.nocomment1105.discordmoderationactions.builder.action.TimeoutActionBuilder
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
 * @param targetUserId The user to ban
 * @param builder Builder lambda used for setting up the ban action
 * @see BanActionBuilder
 */
@Suppress("DuplicatedCode")
public suspend inline fun SlashCommandContext<*, *>.ban(
	targetUserId: Snowflake,
	builder: BanActionBuilder.() -> Unit
): Result {
	val action = BanActionBuilder()
	action.builder()

	if (guild == null) return Result("Guild was null. This command must be run in a guild.")

	action.dmOutcome = sendDm(action.sendDm, event.kord.getUser(targetUserId), action.dmEmbedBuilder).message()

	removeTimeout(action.removeTimeout, event.kord.getUser(targetUserId))

	guild?.ban(targetUserId) {
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

/**
 * DSL function for easily running a ban action.
 * This will ban the user, and carryout any extra tasks specified
 *
 * @param targetUser The user to ban
 * @param builder Builder lambda used for setting up the ban action
 * @see BanActionBuilder
 */
public suspend inline fun SlashCommandContext<*, *>.ban(
	targetUser: User,
	builder: BanActionBuilder.() -> Unit
): Result = ban(targetUser.id, builder)

/**
 * DSL function for easily running a ban action.
 * This will ban the user, and carryout any extra tasks specified
 *
 * @param targetUser The user to ban
 * @param builder Builder lambda used for setting up the ban action
 * @see BanActionBuilder
 */
public suspend inline fun SlashCommandContext<*, *>.ban(
	targetUser: UserBehavior,
	builder: BanActionBuilder.() -> Unit
): Result = ban(targetUser.id, builder)

/**
 * DSL function for easily running a ban action.
 * This will softban the user, and carryout any extra tasks specified
 *
 * @param targetUserId The user to softban
 * @param builder Builder lambda used for setting up the softban action
 * @see SoftBanActionBuilder
 */
@Suppress("DuplicatedCode")
public suspend inline fun SlashCommandContext<*, *>.softban(
	targetUserId: Snowflake,
	builder: SoftBanActionBuilder.() -> Unit
): Result {
	val action = SoftBanActionBuilder()
	action.builder()

	if (guild == null) return Result("Guild was null. This command must be run in a guild.")

	action.dmOutcome = sendDm(action.sendDm, event.kord.getUser(targetUserId), action.dmEmbedBuilder).message()

	removeTimeout(action.removeTimeout, event.kord.getUser(targetUserId))

	guild?.ban(targetUserId) {
		reason = action.reason
		deleteMessageDuration = action.deleteMessageDuration.toDuration(TimeZone.UTC)
	}

	guild?.unban(targetUserId)

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

/**
 * DSL function for easily running a ban action.
 * This will softban the user, and carryout any extra tasks specified
 *
 * @param targetUser The user to softban
 * @param builder Builder lambda used for setting up the softban action
 * @see SoftBanActionBuilder
 */
public suspend inline fun SlashCommandContext<*, *>.softban(
	targetUser: User,
	builder: SoftBanActionBuilder.() -> Unit
): Result = softban(targetUser.id, builder)

/**
 * DSL function for easily running a ban action.
 * This will softban the user, and carryout any extra tasks specified
 *
 * @param targetUser The user to softban
 * @param builder Builder lambda used for setting up the softban action
 * @see SoftBanActionBuilder
 */
public suspend inline fun SlashCommandContext<*, *>.softban(
	targetUser: UserBehavior,
	builder: SoftBanActionBuilder.() -> Unit
): Result = softban(targetUser.id, builder)

/**
 * DSL function for easily running a ban action.
 * This will kick the user, and carryout any extra tasks specified
 *
 * @param targetUserId The user to kick
 * @param builder Builder lambda used for setting up the kick action
 * @see KickActionBuilder
 */
public suspend inline fun SlashCommandContext<*, *>.kick(
	targetUserId: Snowflake,
	builder: KickActionBuilder.() -> Unit
): Result {
	val action = KickActionBuilder()
	action.builder()

	if (guild == null) return Result("Guild was null. This command must be run in a guild.")

	action.dmOutcome = sendDm(action.sendDm, event.kord.getUser(targetUserId), action.dmEmbedBuilder).message()

	removeTimeout(action.removeTimeout, event.kord.getUser(targetUserId))

	guild?.kick(targetUserId, action.reason)

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

/**
 * DSL function for easily running a ban action.
 * This will kick the user, and carryout any extra tasks specified
 *
 * @param targetUser The user to kick
 * @param builder Builder lambda used for setting up the kick action
 * @see KickActionBuilder
 */
public suspend inline fun SlashCommandContext<*, *>.kick(
	targetUser: User,
	builder: KickActionBuilder.() -> Unit
): Result = kick(targetUser.id, builder)

/**
 * DSL function for easily running a ban action.
 * This will kick the user, and carryout any extra tasks specified
 *
 * @param targetUser The user to kick
 * @param builder Builder lambda used for setting up the kick action
 * @see KickActionBuilder
 */
public suspend inline fun SlashCommandContext<*, *>.kick(
	targetUser: UserBehavior,
	builder: KickActionBuilder.() -> Unit
): Result = kick(targetUser.id, builder)

/**
 * DSL function for easily running a ban action.
 * This will time out the user, and carryout any extra tasks specified
 *
 * @param targetUserId The user to timeout
 * @param builder Builder lambda used for setting up the timeout action
 * @see BanActionBuilder
 */
public suspend inline fun SlashCommandContext<*, *>.timeout(
	targetUserId: Snowflake,
	builder: TimeoutActionBuilder.() -> Unit
): Result {
	val action = TimeoutActionBuilder()
	action.builder()

	if (guild == null) return Result("Guild was null. This command must be run in a guild.")

	action.dmOutcome = sendDm(action.sendDm, event.kord.getUser(targetUserId), action.dmEmbedBuilder).message()

	guild?.getMemberOrNull(targetUserId)?.edit { timeoutUntil = action.timeoutDuration }

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

/**
 * DSL function for easily running a ban action.
 * This will time out the user, and carryout any extra tasks specified
 *
 * @param targetUser The user to timeout
 * @param builder Builder lambda used for setting up the timeout action
 * @see BanActionBuilder
 */
public suspend inline fun SlashCommandContext<*, *>.timeout(
	targetUser: User,
	builder: TimeoutActionBuilder.() -> Unit
): Result = timeout(targetUser.id, builder)

/**
 * DSL function for easily running a ban action.
 * This will time out the user, and carryout any extra tasks specified
 *
 * @param targetUser The user to timeout
 * @param builder Builder lambda used for setting up the timeout action
 * @see BanActionBuilder
 */
public suspend inline fun SlashCommandContext<*, *>.timeout(
	targetUser: UserBehavior,
	builder: TimeoutActionBuilder.() -> Unit
): Result = timeout(targetUser.id, builder)
