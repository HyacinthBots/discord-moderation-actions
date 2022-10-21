/*
 * Copyright (c) 2022 NoComment1105 <nocomment1105@outlook.com>
 *
 * This file is part of discord-moderation-actions.
 *
 * Licensed under the MIT license. For more information,
 * please see the LICENSE file or https://mit-license.org/
 */

package io.github.nocomment1105.discordmoderationactions.builder.action

import kotlinx.datetime.DateTimePeriod

public open class SoftBanActionBuilder : BanActionBuilder() {
	public override var deleteMessageDuration: DateTimePeriod = DateTimePeriod(days = 3)
}
