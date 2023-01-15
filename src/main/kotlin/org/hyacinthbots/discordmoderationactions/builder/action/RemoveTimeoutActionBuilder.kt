/*
 * Copyright (c) 2022 HyacinthBots <hyacinthbots@outlook.com>
 *
 * This file is part of discord-moderation-actions.
 *
 * Licensed under the MIT license. For more information,
 * please see the LICENSE file or https://mit-license.org/
 */

package org.hyacinthbots.discordmoderationactions.builder.action

import dev.kord.core.entity.channel.GuildMessageChannel
import dev.kord.rest.builder.message.EmbedBuilder
import org.hyacinthbots.discordmoderationactions.annotations.ActionBuilderDSL
import org.hyacinthbots.discordmoderationactions.builder.RemoveAction
import org.hyacinthbots.discordmoderationactions.enums.DmResult
import org.hyacinthbots.discordmoderationactions.enums.PrivateLogResult

public open class RemoveTimeoutActionBuilder : RemoveAction {
	/** Whether to send a DM about this action to the user. Default: true. */
	public var sendDm: Boolean = true

	/** Whether to send a message to the action log provided. Default: True. */
	public override var sendActionLog: Boolean = true

	/** The reason for the action. */
	public override var reason: String? = "No Reason provided"

	/** The channel to send the [actionEmbed] too. */
	public override var loggingChannel: GuildMessageChannel? = null

	/** The result of the attempt to send a private action log. */
	public override lateinit var privateLogResult: PrivateLogResult

	/** The result of the attempt to send a dm to the user. */
	public lateinit var dmResult: DmResult

	/**
	 * Whether the bot has permission the required permissions to use the logging channel. This should be evaluated
	 * by a check system from within the bot and passed into this variable.
	 */
	public override var hasLogChannelPerms: Boolean? = null

	/** @suppress Builder that shouldn't be set directly by the user. */
	public override var actionEmbedBuilder: (suspend EmbedBuilder.() -> Unit)? = null

	/** @suppress Builder that shouldn't be set directly by the user. */
	public var dmEmbedBuilder: (suspend EmbedBuilder.() -> Unit)? = null

	/**
	 * DSL function used to configure the DM embed.
	 *
	 * @see EmbedBuilder
	 */
	@ActionBuilderDSL
	public fun dmEmbed(builder: suspend EmbedBuilder.() -> Unit) {
		dmEmbedBuilder = builder
	}
}
