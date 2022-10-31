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
 * An enum for the result of public logs.
 *
 * @property message The message attached to the ordinal
 */
public enum class PublicLogResult(public val message: String) {
	/** Represents the success of sending a public action log. */
	PUBLIC_LOG_SUCCESS("Action log sent successfully"),

	/** Represents the failure of sending a public action log. */
	PUBLIC_LOG_FAIL("Action log failed to send"),

	/** Signifies that no attempt to send a public action log was made. */
	PUBLIC_LOG_NOT_SENT("Action Log disabled")
}
