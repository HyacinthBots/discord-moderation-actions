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

public open class KickActionBuilder : Action {
	override var sendDm: Boolean = true

	override var removeTimeout: Boolean = false

	override var sendActionLog: Boolean = true

	public override lateinit var reason: String

	public override lateinit var user: User

	public override lateinit var dmOutcome: String

	public override lateinit var loggingChannel: GuildMessageChannel

	public override var logPublicly: Boolean? = null

	public override var hasLogChannelPerms: Boolean? = null
}
