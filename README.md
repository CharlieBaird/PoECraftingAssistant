# PoE Crafting Assistant

A lightweight and intuitive crafting assistant for Path of Exile. This tool was created to assist players with in-game tasks such as chaos spam. To do so, each time the player clicks an item in-game (with perhaps a chaos orb), the tool parses the item and conditionally plays a sound indicating that the item hit the filter created by the user. The tool can also prevent you from rolling-over an item by blocking your mouse.

The Crafting Assistant comes with a UI to assist users in creating filters. This program is entirely standalone, and does not come with an installer. It runs off of a single .jar file, using resources that come in the downloaded folder. Currently it is only tested and functional on Windows 10. Support for  other platforms may come in the future.

# Getting Started

## Download
Go to the [releases page](https://github.com/CharlieBaird/PoECraftingAssistant/releases) and download the latest release. For instructions in setting up the tool, head over to [getting started](https://github.com/CharlieBaird/PoECraftingAssistant/wiki/1.-Getting-Started) in the [wiki](https://github.com/CharlieBaird/PoECraftingAssistant/wiki).

## Configuring Delay
### **THIS IS AN EXTREMELY IMPORTANT STEP. THE TOOL WILL NOT WORK AS EXPECTED IF NOT ADJUSTED CORRECTLY TO YOUR IN-GAME PING.**
### **IF THE TOOL SEEMS TO NOT BE DETECTING WHEN FILTERS HAVE HIT, IT IS MOST LIKELY BECAUSE THIS VALUE IS TOO LOW. ADJUST AND TEST BEFORE YOU TRUST IT WITH SPAMMING.**

Press F1 while in-game to view your ping to the server you are connected to. Alternatively, logout and it will show you your ping value in the login screen where it asks you to select a server.

To adjust the delay, click the gear in the bottom right. *If you are mid-range, I would strongly suggest rounding up. The higher your ping is, the slower you must craft.*
|   Ping In PoE: (ms)  	|           	| 10 	| 20 	| 30 	| 40 	| 50 	|  60 	|  80 	| 100 	| 120 	| 140 	| 200 	|
|:----------------:	|:---------:	|:--:	|:--:	|:--:	|:--:	|:--:	|:---:	|:---:	|:---:	|:---:	|:---:	|:---:	|
| Suggested Delay: (ms) 	|  Stable Internet:  	| 18 	| 27 	| 36 	| 48 	| 58 	|  72 	|  96 	| 120 	| 144 	| 192 	| 240 	|
|                  	| Unstable Internet: 	| 27 	| 40 	| 54 	| 72 	| 87 	| 108 	| 144 	| 180 	| 216 	| 288 	| 360 	|

# Warnings
- Make sure to use the tool while PoE is in either windowed or windowed-fullscreen mode.
- Do not click your mouse as fast as you can; the tool will not work if you click too fast. The lower your ping, the faster you can click. Do not report an issue that the tool isn't working when you are clicking 20 times a second.

# Frequently Asked Questions

#### Will I be banned for using this tool?
No. This tool is **entirely** compliant with the Path of Exile terms of service for macros and 3rd party tools. It only performs one action on the client per click, a simple Ctrl+C. Because it is a separate window, the popup that blocks your cursor from left-clicking is also compliant with the TOS.

#### How do I create an "or" logic gate?
To create an "or" gate, you can create a subfilter in the GUI. To do so, click the white plus button on the left side of the screen to the right of your created filter. The tool will play the sound if the item hits **any** of the created subfilters.

#### Is this perfect?
No, it's not perfect. Error comes from network instability. For example, the tool will try to parse the item when the Path of Exile server has not responded yet, resulting in the same item being parsed twice. This error is being worked on. The tool also does not quite have every single modifier in the game.

#### Can I move the folder?
Yes, the tool is entirely stand-alone. However, the .jar and the "src" folder **must** remain in the same directory.

# Roadmap
- Auto update

## Demo Video
https://youtu.be/3w9q7ZIyEKU

# Bug reports
If you find a bug, please create an [issue thread](https://github.com/CharlieBaird/PoECraftingAssistant/issues/new). I will try to get back to you as soon as possible.

# Feature requests
Please feel free to send in any feature requests you may have via an [issue thread](https://github.com/CharlieBaird/PoECraftingAssistant/issues/new) as well.

