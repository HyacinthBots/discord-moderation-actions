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
import io.github.nocomment1105.discordmoderationactions.enums.PrivateLogResult

@ActionBuilderDSL
public interface RemoveAction {
	public var sendActionLog: Boolean

	public var reason: String?

	public var loggingChannel: GuildMessageChannel?

	public var privateLogResult: PrivateLogResult

	public var hasLogChannelPerms: Boolean?

	public var actionEmbedBuilder: (suspend EmbedBuilder.() -> Unit)?

	/**
	 * DSL function used to configure the private log embed.
	 *
	 * @see EmbedBuilder
	 */
	@ActionBuilderDSL
	public fun actionEmbed(builder: suspend EmbedBuilder.() -> Unit) {
		actionEmbedBuilder = builder
	}
}
