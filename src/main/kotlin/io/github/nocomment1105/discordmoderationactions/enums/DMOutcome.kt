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
 * An enum for the possible outcomes of a DM message.
 *
 * @property resultMessage The message for the result of the given outcome
 */
public enum class DMOutcome(private val resultMessage: String) {
	SUCCESS("User notified with a direct message"),
	FAIL("Failed to notify user with a message");

	public fun message(): String = resultMessage
}
