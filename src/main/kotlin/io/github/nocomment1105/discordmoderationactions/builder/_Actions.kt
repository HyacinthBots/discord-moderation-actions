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
import com.kotlindiscord.kord.extensions.utils.timeoutUntil
import com.kotlindiscord.kord.extensions.utils.toDuration
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.UserBehavior
import dev.kord.core.behavior.ban
import dev.kord.core.behavior.edit
import io.github.nocomment1105.discordmoderationactions.builder.action.BanActionBuilder
import io.github.nocomment1105.discordmoderationactions.builder.action.KickActionBuilder
import io.github.nocomment1105.discordmoderationactions.builder.action.SoftBanActionBuilder
import io.github.nocomment1105.discordmoderationactions.builder.action.TimeoutActionBuilder
import io.github.nocomment1105.discordmoderationactions.enums.ActionResults
import io.github.nocomment1105.discordmoderationactions.utils.Result
import io.github.nocomment1105.discordmoderationactions.utils.removeTimeout
import io.github.nocomment1105.discordmoderationactions.utils.sendDm
import io.github.nocomment1105.discordmoderationactions.utils.sendPrivateLog
import io.github.nocomment1105.discordmoderationactions.utils.sendPublicLog
import kotlinx.datetime.TimeZone
import mu.KotlinLogging

internal val actionLogger = KotlinLogging.logger("Action Logger")

/**
 * DSL function for easily running a ban action.
 * This will ban the user, and carryout any extra tasks specified
 *
 * @param targetUserId The user to ban
 * @param builder Builder lambda used for setting up the ban action
 * @see BanActionBuilder
 */
public suspend fun SlashCommandContext<*, *>.ban(
	targetUserId: Snowflake,
	builder: BanActionBuilder.() -> Unit
): Result {
	val action = BanActionBuilder()
	action.builder()

	if (guild == null) return Result(ActionResults.ACTION_FAIL)

	val dm = sendDm(action.sendDm, targetUserId, action.dmEmbedBuilder)

	removeTimeout(action.removeTimeout, event.kord.getUser(targetUserId))

	guild?.ban(targetUserId) {
		reason = action.reason
		deleteMessageDuration = action.deleteMessageDuration.toDuration(TimeZone.UTC)
	}

	val publicLog = sendPublicLog(action.logPublicly, action.publicActionEmbedBuilder)

	val privateLog = sendPrivateLog(action.sendActionLog, action.loggingChannel, action.actionEmbedBuilder)

	return Result(ActionResults.ACTION_SUCCESS, dm, privateLog, publicLog)
}

/**
 * DSL function for easily running a ban action.
 * This will ban the user, and carryout any extra tasks specified
 *
 * @param targetUser The user to ban
 * @param builder Builder lambda used for setting up the ban action
 * @see BanActionBuilder
 */
public suspend fun <T : UserBehavior> SlashCommandContext<*, *>.ban(
	targetUser: T,
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
public suspend fun SlashCommandContext<*, *>.softban(
	targetUserId: Snowflake,
	builder: SoftBanActionBuilder.() -> Unit
): Result {
	val action = SoftBanActionBuilder()
	action.builder()

	if (guild == null) return Result(ActionResults.NULL_GUILD)

	val dm = sendDm(action.sendDm, targetUserId, action.dmEmbedBuilder)

	removeTimeout(action.removeTimeout, event.kord.getUser(targetUserId))

	guild?.ban(targetUserId) {
		reason = action.reason
		deleteMessageDuration = action.deleteMessageDuration.toDuration(TimeZone.UTC)
	}

	guild?.unban(targetUserId)

	val publicLog = sendPublicLog(action.logPublicly, action.publicActionEmbedBuilder)

	val privateLog = sendPrivateLog(action.sendActionLog, action.loggingChannel, action.actionEmbedBuilder)

	return Result(ActionResults.ACTION_SUCCESS, dm, privateLog, publicLog)
}

/**
 * DSL function for easily running a ban action.
 * This will softban the user, and carryout any extra tasks specified
 *
 * @param targetUser The user to softban
 * @param builder Builder lambda used for setting up the softban action
 * @see SoftBanActionBuilder
 */
public suspend fun <T : UserBehavior> SlashCommandContext<*, *>.softban(
	targetUser: T,
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
public suspend fun SlashCommandContext<*, *>.kick(
	targetUserId: Snowflake,
	builder: KickActionBuilder.() -> Unit
): Result {
	val action = KickActionBuilder()
	action.builder()

	if (guild == null) return Result(ActionResults.NULL_GUILD)

	val dm = sendDm(action.sendDm, targetUserId, action.dmEmbedBuilder)

	removeTimeout(action.removeTimeout, event.kord.getUser(targetUserId))

	guild?.kick(targetUserId, action.reason)

	val publicLog = sendPublicLog(action.logPublicly, action.publicActionEmbedBuilder)

	val privateLog = sendPrivateLog(action.sendActionLog, action.loggingChannel, action.actionEmbedBuilder)

	return Result(ActionResults.ACTION_SUCCESS, dm, privateLog, publicLog)
}

/**
 * DSL function for easily running a ban action.
 * This will kick the user, and carryout any extra tasks specified
 *
 * @param targetUser The user to kick
 * @param builder Builder lambda used for setting up the kick action
 * @see KickActionBuilder
 */
public suspend fun <T : UserBehavior> SlashCommandContext<*, *>.kick(
	targetUser: T,
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
public suspend fun SlashCommandContext<*, *>.timeout(
	targetUserId: Snowflake,
	builder: TimeoutActionBuilder.() -> Unit
): Result {
	val action = TimeoutActionBuilder()
	action.builder()

	if (guild == null) return Result(ActionResults.NULL_GUILD)

	val dm = sendDm(action.sendDm, targetUserId, action.dmEmbedBuilder)

	guild?.getMemberOrNull(targetUserId)?.edit { timeoutUntil = action.timeoutDuration }

	val publicLog = sendPublicLog(action.logPublicly, action.publicActionEmbedBuilder)

	val privateLog = sendPrivateLog(action.sendActionLog, action.loggingChannel, action.actionEmbedBuilder)

	return Result(ActionResults.ACTION_SUCCESS, dm, privateLog, publicLog)
}

/**
 * DSL function for easily running a ban action.
 * This will time out the user, and carryout any extra tasks specified
 *
 * @param targetUser The user to timeout
 * @param builder Builder lambda used for setting up the timeout action
 * @see BanActionBuilder
 */
public suspend fun <T : UserBehavior> SlashCommandContext<*, *>.timeout(
	targetUser: T,
	builder: TimeoutActionBuilder.() -> Unit
): Result = timeout(targetUser.id, builder)
