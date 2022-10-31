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
import io.github.nocomment1105.discordmoderationactions.builder.action.RemoveTimeoutActionBuilder
import io.github.nocomment1105.discordmoderationactions.builder.action.SoftBanActionBuilder
import io.github.nocomment1105.discordmoderationactions.builder.action.TimeoutActionBuilder
import io.github.nocomment1105.discordmoderationactions.builder.action.UnbanActionBuilder
import io.github.nocomment1105.discordmoderationactions.enums.ActionResults
import io.github.nocomment1105.discordmoderationactions.enums.DmResult
import io.github.nocomment1105.discordmoderationactions.enums.PublicLogResult
import io.github.nocomment1105.discordmoderationactions.utils.Result
import io.github.nocomment1105.discordmoderationactions.utils.removeTimeout
import io.github.nocomment1105.discordmoderationactions.utils.sendDm
import io.github.nocomment1105.discordmoderationactions.utils.sendPrivateLog
import io.github.nocomment1105.discordmoderationactions.utils.sendPublicLog
import kotlinx.coroutines.flow.toList
import kotlinx.datetime.TimeZone
import mu.KotlinLogging

internal val actionLogger = KotlinLogging.logger("Action Logger")

/**
 * DSL function for easily running a ban action.
 * This will ban the user, and carryout any extra tasks specified
 *
 * @param targetUserId The id of the user to ban
 * @param builder Builder lambda used for setting up the ban action
 * @see BanActionBuilder
 */
public suspend fun SlashCommandContext<*, *>.ban(
	targetUserId: Snowflake,
	builder: suspend BanActionBuilder.() -> Unit
): Result {
	val action = BanActionBuilder()
	action.builder()

	if (guild == null) return Result(ActionResults.ACTION_FAIL)

	action.dmResult = sendDm(action.sendDm, targetUserId, action.dmEmbedBuilder)

	removeTimeout(action.removeTimeout, guild?.getMemberOrNull(targetUserId))

	guild?.ban(targetUserId) {
		reason = action.reason
		deleteMessageDuration = action.deleteMessageDuration.toDuration(TimeZone.UTC)
	}

	val publicLog = sendPublicLog(action.logPublicly, action.publicActionEmbedBuilder)

	val privateLog = sendPrivateLog(
		action.sendActionLog,
		action.loggingChannel,
		action.hasLogChannelPerms,
		action.actionEmbedBuilder
	)

	return Result(ActionResults.ACTION_SUCCESS, action.dmResult, privateLog, publicLog)
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
	builder: suspend BanActionBuilder.() -> Unit
): Result = ban(targetUser.id, builder)

/**
 * DSL function for easily running a ban action.
 * This will softban the user, and carryout any extra tasks specified
 *
 * @param targetUserId The id of the user to softban
 * @param builder Builder lambda used for setting up the softban action
 * @see SoftBanActionBuilder
 */
@Suppress("DuplicatedCode")
public suspend fun SlashCommandContext<*, *>.softban(
	targetUserId: Snowflake,
	builder: suspend SoftBanActionBuilder.() -> Unit
): Result {
	val action = SoftBanActionBuilder()
	action.builder()

	if (guild == null) return Result(ActionResults.NULL_GUILD)

	action.dmResult = sendDm(action.sendDm, targetUserId, action.dmEmbedBuilder)

	removeTimeout(action.removeTimeout, guild?.getMemberOrNull(targetUserId))

	guild?.ban(targetUserId) {
		reason = action.reason + "**SOFT-BAN**"
		deleteMessageDuration = action.deleteMessageDuration.toDuration(TimeZone.UTC)
	}

	guild?.unban(targetUserId, "Soft-ban unban")

	val publicLog = sendPublicLog(action.logPublicly, action.publicActionEmbedBuilder)

	val privateLog = sendPrivateLog(
		action.sendActionLog,
		action.loggingChannel,
		action.hasLogChannelPerms,
		action.actionEmbedBuilder
	)

	return Result(ActionResults.ACTION_SUCCESS, action.dmResult, privateLog, publicLog)
}

/**
 * DSL function for easily running a softban action.
 * This will softban the user, and carryout any extra tasks specified
 *
 * @param targetUser The user to softban
 * @param builder Builder lambda used for setting up the softban action
 * @see SoftBanActionBuilder
 */
public suspend fun <T : UserBehavior> SlashCommandContext<*, *>.softban(
	targetUser: T,
	builder: suspend SoftBanActionBuilder.() -> Unit
): Result = softban(targetUser.id, builder)

/**
 * DSL function for easily running an unban action.
 * This will unban the user, and carryout any extra tasks specified
 *
 * @param targetUserId The id of the user to unban
 * @param builder Builder lambda used for setting up the unban action
 * @see UnbanActionBuilder
 */
public suspend fun SlashCommandContext<*, *>.unban(
	targetUserId: Snowflake,
	builder: suspend UnbanActionBuilder.() -> Unit
): Result {
	val action = UnbanActionBuilder()
	action.builder()

	if (guild == null) return Result(ActionResults.NULL_GUILD)

	if (targetUserId in guild!!.bans.toList().map { it.userId }) {
		guild?.unban(targetUserId, action.reason)
	} else {
		return Result(ActionResults.ACTION_FAIL, extraInfo = "**Error:** User is not banned")
	}

	val privateLog = sendPrivateLog(
		action.sendActionLog,
		action.loggingChannel,
		action.hasLogChannelPerms,
		action.actionEmbedBuilder
	)

	return Result(
		ActionResults.ACTION_SUCCESS,
		DmResult.DM_NOT_SENT,
		privateLog,
		PublicLogResult.PUBLIC_LOG_NOT_SENT
	)
}

/**
 * DSL function for easily running an unban action.
 * This will unban the user, and carryout any extra tasks specified
 *
 * @param targetUser The user to unban
 * @param builder Builder lambda used for setting up the unban action
 * @see UnbanActionBuilder
 */
public suspend fun <T : UserBehavior> SlashCommandContext<*, *>.unban(
	targetUser: T,
	builder: suspend UnbanActionBuilder.() -> Unit
): Result = unban(targetUser.id, builder)

/**
 * DSL function for easily running a kick action.
 * This will kick the user, and carryout any extra tasks specified
 *
 * @param targetUserId The id of the user to kick
 * @param builder Builder lambda used for setting up the kick action
 * @see KickActionBuilder
 */
public suspend fun SlashCommandContext<*, *>.kick(
	targetUserId: Snowflake,
	builder: suspend KickActionBuilder.() -> Unit
): Result {
	val action = KickActionBuilder()
	action.builder()

	if (guild == null) return Result(ActionResults.NULL_GUILD)

	action.dmResult = sendDm(action.sendDm, targetUserId, action.dmEmbedBuilder)

	removeTimeout(action.removeTimeout, guild?.getMemberOrNull(targetUserId))

	guild?.kick(targetUserId, action.reason)

	val publicLog = sendPublicLog(action.logPublicly, action.publicActionEmbedBuilder)

	val privateLog = sendPrivateLog(
		action.sendActionLog,
		action.loggingChannel,
		action.hasLogChannelPerms,
		action.actionEmbedBuilder
	)

	return Result(ActionResults.ACTION_SUCCESS, action.dmResult, privateLog, publicLog)
}

/**
 * DSL function for easily running a kick action.
 * This will kick the user, and carryout any extra tasks specified
 *
 * @param targetUser The user to kick
 * @param builder Builder lambda used for setting up the kick action
 * @see KickActionBuilder
 */
public suspend fun <T : UserBehavior> SlashCommandContext<*, *>.kick(
	targetUser: T,
	builder: suspend KickActionBuilder.() -> Unit
): Result = kick(targetUser.id, builder)

/**
 * DSL function for easily running a timeout action.
 * This will time out the user, and carryout any extra tasks specified
 *
 * @param targetUserId The id of the user to timeout
 * @param builder Builder lambda used for setting up the timeout action
 * @see TimeoutActionBuilder
 */
public suspend fun SlashCommandContext<*, *>.timeout(
	targetUserId: Snowflake,
	builder: suspend TimeoutActionBuilder.() -> Unit
): Result {
	val action = TimeoutActionBuilder()
	action.builder()

	if (guild == null) return Result(ActionResults.NULL_GUILD)

	action.dmResult = sendDm(action.sendDm, targetUserId, action.dmEmbedBuilder)

	guild?.getMemberOrNull(targetUserId)?.edit { timeoutUntil = action.timeoutDuration }

	val publicLog = sendPublicLog(action.logPublicly, action.publicActionEmbedBuilder)

	val privateLog = sendPrivateLog(
		action.sendActionLog,
		action.loggingChannel,
		action.hasLogChannelPerms,
		action.actionEmbedBuilder
	)

	return Result(ActionResults.ACTION_SUCCESS, action.dmResult, privateLog, publicLog)
}

/**
 * DSL function for easily running a timeout action.
 * This will time out the user, and carryout any extra tasks specified.
 *
 * @param targetUser The user to timeout
 * @param builder Builder lambda used for setting up the timeout action
 * @see TimeoutActionBuilder
 */
public suspend fun <T : UserBehavior> SlashCommandContext<*, *>.timeout(
	targetUser: T,
	builder: suspend TimeoutActionBuilder.() -> Unit
): Result = timeout(targetUser.id, builder)

/**
 * DSL function for easily running a remove timeout action.
 * This will remove the timeout from a user, and carryout any extra tasks specified.
 *
 * @param targetUserId The id of the user to remove the timeout from
 * @param builder Builder lambda used for setting up the remove timeout action
 * @see RemoveTimeoutActionBuilder
 */
public suspend fun SlashCommandContext<*, *>.removeTimeout(
	targetUserId: Snowflake,
	builder: suspend RemoveTimeoutActionBuilder.() -> Unit
): Result {
	val action = RemoveTimeoutActionBuilder()
	action.builder()

	if (guild == null) return Result(ActionResults.NULL_GUILD)

	action.dmResult = sendDm(action.sendDm, targetUserId, action.dmEmbedBuilder)

	removeTimeout(true, guild?.getMemberOrNull(targetUserId))

	val privateLog = sendPrivateLog(
		action.sendActionLog,
		action.loggingChannel,
		action.hasLogChannelPerms,
		action.actionEmbedBuilder
	)

	return Result(ActionResults.ACTION_SUCCESS, action.dmResult, privateLog, PublicLogResult.PUBLIC_LOG_NOT_SENT)
}

/**
 * DSL function for easily running a remove timeout action.
 * This will remove the timeout from a user, and carryout any extra tasks specified.
 *
 * @param targetUser The user to remove the timeout from
 * @param builder Builder lambda used for setting up the remove timeout action
 * @see RemoveTimeoutActionBuilder
 */
public suspend fun <T : UserBehavior> SlashCommandContext<*, *>.removeTimeout(
	targetUser: T,
	builder: suspend RemoveTimeoutActionBuilder.() -> Unit
): Result = removeTimeout(targetUser.id, builder)
