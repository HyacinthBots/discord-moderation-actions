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

/**
 * An interface containing the common fields for actions.
 */
public interface Action {

	/** Whether to send a DM about this action to the user. Default: true. */
	public var sendDm: Boolean

	/** Whether to remove any outstanding timeout the user has applied to them before banning. Default: false. */
	public var removeTimeout: Boolean

	/** Whether to send a message to the action log provided. Default: True. */
	public var sendActionLog: Boolean

	/** The reason for the action. */
	public var reason: String

	/** The target user for the action. */
	public val user: User

	/** The outcome of DMing the [user]. */
	public var dmOutcome: String

	/** The channel to send the [actionEmbed] too. */
	public val loggingChannel: GuildMessageChannel

	/** Whether to log the action publicly. I.E in the channel the command was run in. */
	public var logPublicly: Boolean?

	/**
	 * Whether the bot has permission the required permissions to use the logging channel. This should be evaluated
	 * by a check system from within the bot and passed into this variable.
	 */
	public var hasLogChannelPerms: Boolean?

	/** @suppress Builder that shouldn't be set directly by the user. */
	public val dmEmbedBuilder: EmbedBuilder
		get() = EmbedBuilder()

	/** @suppress Builder that shouldn't be set directly by the user. */
	public val actionEmbedBuilder: EmbedBuilder
		get() = EmbedBuilder()

	/** @suppress Builder that shouldn't be set directly by the user. */
	public val publicActionEmbedBuilder: EmbedBuilder
		get() = EmbedBuilder()

	/**
	 * DSL function used to configure the DM embed.
	 *
	 * @see EmbedBuilder
	 */
	public fun dmEmbed(builder: EmbedBuilder.() -> Unit) {
		builder(dmEmbedBuilder)
	}

	/**
	 * DSL function used to configure the Action log embed.
	 *
	 * @see EmbedBuilder
	 */
	public fun actionEmbed(builder: EmbedBuilder.() -> Unit) {
		builder(actionEmbedBuilder)
	}

	/**
	 * DSL function used to configure the DM embed.
	 *
	 * @see EmbedBuilder
	 */
	public fun publicActionEmbed(builder: EmbedBuilder.() -> Unit) {
		builder(publicActionEmbedBuilder)
	}
}
