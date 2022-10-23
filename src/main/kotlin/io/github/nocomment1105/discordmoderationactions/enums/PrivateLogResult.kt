/*
 * Copyright (c) 2022 NoComment1105 <nocomment1105@outlook.com>
 *
 * This file is part of discord-moderation-actions.
 *
 * Licensed under the MIT license. For more information,
 * please see the LICENSE file or https://mit-license.org/
 */

package io.github.nocomment1105.discordmoderationactions.enums

/**
 * An enum for the result of private action logs.
 *
 * @property message The message attached to the ordinal
 */
public enum class PrivateLogResult(public val message: String) {
	/** Represents the success of an attempt at sending an action log. */
	LOG_SUCCESS("Action log sent successfully"),

	/** Represents the failure of an attempt at sending an action log. */
	LOG_FAIL("Action log failed to send"),

	/** Signifies that no attempt to send an action log was made. */
	LOG_NOT_SENT("Action Log disabled")
}
