package com.example.as1.utils;

import com.example.as1.CharSelActivity;
import com.example.as1.Character;
import com.example.as1.LoginActivity;
import com.example.as1.World;
import com.example.as1.WorldSelActivity;

/**
 * Contains necessary userdata to pull between screens.
 * username: login
 * character: character select
 * charname: character select
 * worldType: world select/create
 * worldName: world select/create
 * worldDiff: world select/create
 * worldid: world select/create
 * userid: login
 * worldJoin: play
 */
public class Userdata {
	public static String username = null;
	public static Character character = null;
	public static String charname = null;
	public static String worldType = null;
	public static String worldName = null;
	public static char worldDiff;
	public static int worldid, userid, worldJoin;

	/**
	 * sets the variable username to LoginActivity's username
	 * @return username
	 */
	public static String getUser() {
		username = LoginActivity.getUser();
		return username;
	}

	/**
	 * sets the variable character to CharSelActivity's character
	 * @return character
	 */
	public static Character getCharacter() {
		character = CharSelActivity.getCharacter();
		return character;
	}

}