/*
 * Copyright (c) 2022 NoComment1105 <nocomment1105@outlook.com>
 *
 * This file is part of discord-moderation-actions.
 *
 * Licensed under the MIT license. For more information,
 * please see the LICENSE file or https://mit-license.org/
 */

@file:Suppress("TooGenericExceptionCaught")

package io.github.nocomment1105.discordmoderationactions.utils

import com.kotlindiscord.kord.extensions.checks.guildFor
import com.kotlindiscord.kord.extensions.commands.application.slash.SlashCommandContext
import com.kotlindiscord.kord.extensions.utils.dm
import com.kotlindiscord.kord.extensions.utils.timeoutUntil
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.channel.MessageChannelBehavior
import dev.kord.core.behavior.channel.createMessage
import dev.kord.core.behavior.edit
import dev.kord.core.entity.User
import dev.kord.rest.builder.message.EmbedBuilder
import io.github.nocomment1105.discordmoderationactions.builder.actionLogger
import io.github.nocomment1105.discordmoderationactions.enums.ActionLogResult
import io.github.nocomment1105.discordmoderationactions.enums.DmResult
import io.github.nocomment1105.discordmoderationactions.enums.PublicActionLogResult
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Removes a timeout from a provided [user] if [removeTimeout] is true.
 *
 * @param removeTimeout Whether to remove the timeout or not
 * @param user The user to remove the timeout from
 */
public suspend inline fun SlashCommandContext<*, *>.removeTimeout(removeTimeout: Boolean, user: User?) {
	if (removeTimeout && user != null) {
		user.asMemberOrNull(guildFor(event)!!.id)?.edit { timeoutUntil = null }
	}
}

/**
 * Sends a DM to the [target User][targetUserId] and returns the [DmResult].
 *
 * @param shouldDm Whether to dm the user or not
 * @param targetUserId The ID of the user to DM
 * @param dmEmbedBuilder The builder for the dm embed to send
 *
 * @return [DmResult] ordinal based on the success
 */
internal suspend inline fun SlashCommandContext<*, *>.sendDm(
	shouldDm: Boolean,
	targetUserId: Snowflake,
	noinline dmEmbedBuilder: (suspend EmbedBuilder.() -> Unit)?
): DmResult =
	if (shouldDm && dmEmbedBuilder != null) {
		try {
			event.kord.getUser(targetUserId)?.dm {
				embeds.add(EmbedBuilder().applyBuilder(dmEmbedBuilder))
			}
			DmResult.DM_SUCCESS
		} catch (e: Exception) {
			actionLogger.error(e) { e.message }
			DmResult.DM_FAIL
		}
	} else {
		DmResult.DM_NOT_SENT
}

/**
 * Sends a public log to the channel the command was run it, and returns the [PublicActionLogResult].
 *
 * @param shouldLog Whether to send a log or not
 * @param publicLogEmbedBuilder The builder for the embed to send
 *
 * @return [PublicActionLogResult] ordinal based on the success
 */
internal suspend inline fun SlashCommandContext<*, *>.sendPublicLog(
	shouldLog: Boolean?,
	noinline publicLogEmbedBuilder: (suspend EmbedBuilder.() -> Unit)?
): PublicActionLogResult =
	if (shouldLog == true && publicLogEmbedBuilder != null) {
		try {
			channel.createMessage {
				embeds.add(EmbedBuilder().applyBuilder(publicLogEmbedBuilder))
			}
			PublicActionLogResult.PUBLIC_LOG_SUCCESS
		} catch (e: Exception) {
			actionLogger.error(e) { e.message }
			PublicActionLogResult.PUBLIC_LOG_FAIL
		}
	} else {
		PublicActionLogResult.PUBLIC_LOG_NOT_SENT
	}

/**
 * Sends a private log to a [channel] and returns the [ActionLogResult].
 *
 * @param shouldLog Whether to send a log or not
 * @param channel The channel to send the message in
 * @param actionEmbedBuilder The builder for the embed to send
 *
 * @return [ActionLogResult] ordinal based on the success
 */
internal suspend inline fun sendPrivateLog(
	shouldLog: Boolean?,
	channel: MessageChannelBehavior?,
	noinline actionEmbedBuilder: (suspend EmbedBuilder.() -> Unit)?
): ActionLogResult =
	if (shouldLog == true && actionEmbedBuilder != null && channel != null) {
		try {
			channel.createMessage {
				embeds.add(EmbedBuilder().applyBuilder(actionEmbedBuilder))
			}
			ActionLogResult.LOG_SUCCESS
		} catch (e: Exception) {
			actionLogger.error(e) { e.message }
			ActionLogResult.LOG_FAIL
		}
	} else {
		ActionLogResult.LOG_NOT_SENT
	}

@OptIn(ExperimentalContracts::class)
public suspend inline fun <T> T.applyBuilder(crossinline block: suspend T.() -> Unit): T {
	contract {
		callsInPlace(block, InvocationKind.EXACTLY_ONCE)
	}
	block()
	return this
}
