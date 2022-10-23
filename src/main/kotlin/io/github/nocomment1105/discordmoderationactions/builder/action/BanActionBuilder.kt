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
import kotlinx.datetime.DateTimePeriod

@ActionBuilderDSL
public open class BanActionBuilder : Action {
	public open lateinit var deleteMessageDuration: DateTimePeriod

	public var removeTimeout: Boolean = false

	public override var sendDm: Boolean = true

	public override var sendActionLog: Boolean = true

	public override var reason: String? = "No reason provided"

	public override var loggingChannel: GuildMessageChannel? = null

	public override var logPublicly: Boolean? = null

	public override var hasLogChannelPerms: Boolean? = null

	public override var dmEmbedBuilder: (suspend EmbedBuilder.() -> Unit)? = null

	public override var actionEmbedBuilder: (suspend EmbedBuilder.() -> Unit)? = null

	public override var publicActionEmbedBuilder: (suspend EmbedBuilder.() -> Unit)? = null
}
