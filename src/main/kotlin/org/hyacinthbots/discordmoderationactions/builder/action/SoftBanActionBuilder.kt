/*
 * Copyright (c) 2022 HyacinthBots <hyacinthbots@outlook.com>
 *
 * This file is part of discord-moderation-actions.
 *
 * Licensed under the MIT license. For more information,
 * please see the LICENSE file or https://mit-license.org/
 */

package org.hyacinthbots.discordmoderationactions.builder.action

import kotlinx.datetime.DateTimePeriod
import org.hyacinthbots.discordmoderationactions.annotations.ActionBuilderDSL

@ActionBuilderDSL
public open class SoftBanActionBuilder : BanActionBuilder() {
	/** The duration into the past of message from this user to delete. Default: 3 days. */
	public override var deleteMessageDuration: DateTimePeriod = DateTimePeriod(days = 3)
}
