/*
 * Copyright (c) 2022 NoComment1105 <nocomment1105@outlook.com>
 *
 * This file is part of discord-moderation-actions.
 *
 * Licensed under the MIT license. For more information,
 * please see the LICENSE file or https://mit-license.org/
 */

package io.github.nocomment1105.discordmoderationactions.utils

import io.github.nocomment1105.discordmoderationactions.enums.ActionResults
import io.github.nocomment1105.discordmoderationactions.enums.DmResult
import io.github.nocomment1105.discordmoderationactions.enums.PrivateLogResult
import io.github.nocomment1105.discordmoderationactions.enums.PublicActionLogResult

/**
 * The result of an action.
 *
 * @property result The overall result of the action
 * @property dmResult The result of dming the user
 * @property logResult The result of the private log
 * @property publicLogResult the result of the public log
 *
 */
public data class Result(
	public val result: ActionResults,
	public val dmResult: DmResult? = null,
	public val logResult: PrivateLogResult? = null,
	public val publicLogResult: PublicActionLogResult? = null
)
