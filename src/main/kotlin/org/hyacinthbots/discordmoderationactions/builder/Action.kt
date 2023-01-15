/*
 * Copyright (c) 2022 HyacinthBots <hyacinthbots@outlook.com>
 *
 * This file is part of discord-moderation-actions.
 *
 * Licensed under the MIT license. For more information,
 * please see the LICENSE file or https://mit-license.org/
 */

package org.hyacinthbots.discordmoderationactions.builder

import dev.kord.core.entity.channel.GuildMessageChannel
import dev.kord.rest.builder.message.EmbedBuilder
import org.hyacinthbots.discordmoderationactions.annotations.ActionBuilderDSL
import org.hyacinthbots.discordmoderationactions.enums.DmResult
import org.hyacinthbots.discordmoderationactions.enums.PrivateLogResult
import org.hyacinthbots.discordmoderationactions.enums.PublicLogResult

/**
 * An interface containing the common fields for actions.
 */
@ActionBuilderDSL
public interface Action {
	public var sendDm: Boolean

	public var sendActionLog: Boolean

	public var reason: String?

	public var loggingChannel: GuildMessageChannel?

	public var logPublicly: Boolean?

	public var dmResult: DmResult

	public var publicLogResult: PublicLogResult

	public var privateLogResult: PrivateLogResult

	public var hasLogChannelPerms: Boolean?

	public var dmEmbedBuilder: (suspend EmbedBuilder.() -> Unit)?

	public var actionEmbedBuilder: (suspend EmbedBuilder.() -> Unit)?

	public var publicActionEmbedBuilder: (suspend EmbedBuilder.() -> Unit)?

	/**
	 * DSL function used to configure the DM embed.
	 *
	 * @see EmbedBuilder
	 */
	@ActionBuilderDSL
	public fun dmEmbed(builder: suspend EmbedBuilder.() -> Unit) {
		dmEmbedBuilder = builder
	}

	/**
	 * DSL function used to configure the Action log embed.
	 *
	 * @see EmbedBuilder
	 */
	@ActionBuilderDSL
	public fun actionEmbed(builder: suspend EmbedBuilder.() -> Unit) {
		actionEmbedBuilder = builder
	}

	/**
	 * DSL function used to configure the DM embed.
	 *
	 * @see EmbedBuilder
	 */
	@ActionBuilderDSL
	public fun publicActionEmbed(builder: suspend EmbedBuilder.() -> Unit) {
		publicActionEmbedBuilder = builder
	}
}
