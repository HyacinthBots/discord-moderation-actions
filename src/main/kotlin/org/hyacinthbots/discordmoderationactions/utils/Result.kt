/*
 * Copyright (c) 2022 HyacinthBots <hyacinthbots@outlook.com>
 *
 * This file is part of discord-moderation-actions.
 *
 * Licensed under the MIT license. For more information,
 * please see the LICENSE file or https://mit-license.org/
 */

package org.hyacinthbots.discordmoderationactions.utils

import org.hyacinthbots.discordmoderationactions.enums.ActionResults
import org.hyacinthbots.discordmoderationactions.enums.DmResult
import org.hyacinthbots.discordmoderationactions.enums.PrivateLogResult
import org.hyacinthbots.discordmoderationactions.enums.PublicLogResult

/**
 * The result of an action.
 *
 * @property result The overall result of the action
 * @property dmResult The result of dming the user
 * @property logResult The result of the private log
 * @property publicLogResult the result of the public log
 * @property extraInfo Any extra info for the result.
 *
 */
public data class Result(
    public val result: ActionResults,
    public val dmResult: DmResult? = null,
    public val logResult: PrivateLogResult? = null,
    public val publicLogResult: PublicLogResult? = null,
    public val extraInfo: String? = null
)
