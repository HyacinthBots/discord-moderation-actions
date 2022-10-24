/*
 * Copyright (c) 2022 NoComment1105 <nocomment1105@outlook.com>
 *
 * This file is part of discord-moderation-actions.
 *
 * Licensed under the MIT license. For more information,
 * please see the LICENSE file or https://mit-license.org/
 */

package io.github.nocomment1105.discordmoderationactions.builder.action

import io.github.nocomment1105.discordmoderationactions.annotations.ActionBuilderDSL
import kotlinx.datetime.DateTimePeriod

@ActionBuilderDSL
public open class SoftBanActionBuilder : BanActionBuilder() {
	/** The duration into the past of message from this user to delete. Default: 3 days. */
	public override var deleteMessageDuration: DateTimePeriod = DateTimePeriod(days = 3)
}
