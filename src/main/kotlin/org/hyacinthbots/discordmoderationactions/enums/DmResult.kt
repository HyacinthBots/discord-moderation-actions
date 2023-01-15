/*
 * Copyright (c) 2022 HyacinthBots <hyacinthbots@outlook.com>
 *
 * This file is part of discord-moderation-actions.
 *
 * Licensed under the MIT license. For more information,
 * please see the LICENSE file or https://mit-license.org/
 */

package org.hyacinthbots.discordmoderationactions.enums

/**
 * An enum for the result of dms.
 *
 * @property message The message attached to the ordinal
 */
public enum class DmResult(public val message: String) {
	/** Represents the success of an attempt at sending a DM to a user. */
	DM_SUCCESS("User notified with direct message"),

	/** Represents the failure of an attempt at sending a DM to a user. */
	DM_FAIL("Failed to notify user with direct message"),

	/** Signifies that no attempt to send a DM was made. */
	DM_NOT_SENT("DM Notification disabled")
}
