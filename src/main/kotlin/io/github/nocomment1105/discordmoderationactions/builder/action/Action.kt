/*
 * Copyright (c) 2022 NoComment1105 <nocomment1105@outlook.com>
 *
 * This file is part of discord-moderation-actions.
 *
 * Licensed under the MIT license. For more information,
 * please see the LICENSE file or https://mit-license.org/
 */

package io.github.nocomment1105.discordmoderationactions.builder.action

import dev.kord.core.entity.channel.GuildMessageChannel
import dev.kord.rest.builder.message.EmbedBuilder
import io.github.nocomment1105.discordmoderationactions.annotations.ActionBuilderDSL

/**
 * An interface containing the common fields for actions.
 */
@ActionBuilderDSL
public interface Action {

	/** Whether to send a DM about this action to the user. Default: true. */
	public var sendDm: Boolean

	/** Whether to send a message to the action log provided. Default: True. */
	public var sendActionLog: Boolean

	/** The reason for the action. */
	public var reason: String?

	/** The channel to send the [actionEmbed] too. */
	public var loggingChannel: GuildMessageChannel?

	/** Whether to log the action publicly. I.E in the channel the command was run in. */
	public var logPublicly: Boolean?

	/**
	 * Whether the bot has permission the required permissions to use the logging channel. This should be evaluated
	 * by a check system from within the bot and passed into this variable.
	 */
	public var hasLogChannelPerms: Boolean?

	/** @suppress Builder that shouldn't be set directly by the user. */
	public var dmEmbedBuilder: (suspend EmbedBuilder.() -> Unit)?

	/** @suppress Builder that shouldn't be set directly by the user. */
	public var actionEmbedBuilder: (suspend EmbedBuilder.() -> Unit)?

	/** @suppress Builder that shouldn't be set directly by the user. */
	public var publicActionEmbedBuilder: (suspend EmbedBuilder.() -> Unit)?

	/**
	 * DSL function used to configure the DM embed.
	 *
	 * @see EmbedBuilder
	 */
	@ActionBuilderDSL
	public suspend fun dmEmbed(builder: suspend EmbedBuilder.() -> Unit) {
		dmEmbedBuilder = builder
	}

	/**
	 * DSL function used to configure the Action log embed.
	 *
	 * @see EmbedBuilder
	 */
	@ActionBuilderDSL
	public suspend fun actionEmbed(builder: suspend EmbedBuilder.() -> Unit) {
		actionEmbedBuilder = builder
	}

	/**
	 * DSL function used to configure the DM embed.
	 *
	 * @see EmbedBuilder
	 */
	@ActionBuilderDSL
	public suspend fun publicActionEmbed(builder: suspend EmbedBuilder.() -> Unit) {
		publicActionEmbedBuilder = builder
	}
}
