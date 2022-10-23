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
 * An enum for the result of actions.
 *
 * @property message The message attached to the ordinal
 */
public enum class ActionResults(public val message: String) {
	/** Represents the overall success of an action. */
	ACTION_SUCCESS("The action has succeeded"),

	/** Represents the overall failure of an action. */
	ACTION_FAIL("The action has failed"),

	/** Signifies that the guild the command was run in was null, suggesting it was in DMs or elsewhere. */
	NULL_GUILD("The Guild this command was run in was null. Please make sure you are in a guild!")
}
