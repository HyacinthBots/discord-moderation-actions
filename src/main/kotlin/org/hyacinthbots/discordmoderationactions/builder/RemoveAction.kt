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
import dev.kord.rest.builder.message.create.UserMessageCreateBuilder
import org.hyacinthbots.discordmoderationactions.annotations.ActionBuilderDSL
import org.hyacinthbots.discordmoderationactions.enums.PrivateLogResult

@ActionBuilderDSL
public interface RemoveAction {
	public var sendActionLog: Boolean

	public var reason: String?

	public var loggingChannel: GuildMessageChannel?

	public var privateLogResult: PrivateLogResult

	public var hasLogChannelPerms: Boolean?

	public var actionEmbedBuilder: (suspend UserMessageCreateBuilder.() -> Unit)?

	/**
	 * DSL function used to configure the private log embed.
	 *
	 * @see EmbedBuilder
	 */
	@ActionBuilderDSL
	public fun actionEmbed(builder: suspend UserMessageCreateBuilder.() -> Unit) {
		actionEmbedBuilder = builder
	}
}
