# PoE Crafting Assistant
### **Currently, this tool is only compatible with Path of Exile 3.10. It will be updated as soon as possible for Harvest League 3.11.**

A lightweight and intuitive crafting assistant for Path of Exile. This tool was created to assist players with in-game tasks such as chaos spam. To do so, each time the player clicks an item in-game (with perhaps a chaos orb), the tool parses the item and conditionally plays a sound indicating that the item hit the filter created by the user.

The Crafting Assistant comes with a UI to assist users in creating filters. This program is entirely standalone, and does not come with an installer. It runs off of a single .jar file, using resources that come in the downloaded folder. Currently it is only tested and functional on Windows 10 with 16:9 monitors. Support for other aspect ratios and other platforms may come in the future.

# Download
Only alpha versions have been released; the download is no longer available. A new download will be created when the tool is in a more stable and improved state.

# Getting Started

## Demo Video
https://youtu.be/3w9q7ZIyEKU

## System Requirements
1. The program will only run on a Windows computer. I do not currently have the capability to test it on Mac/Linux. Please create an issue request if you'd like me to look into this. I can figure something out.
2. You will need at *least* java 8 installed on your computer. Most computers already have this installed.

## Opening
You should be able to simply double click the downloaded .jar file. If this does not work, try following these [instructions](https://imgur.com/a/hBRzCT5 "Instructions"). If it throws an error/exception, please create an issue on the GitHub page.

## Creating Filters
Once the program is opened, you are presented with the GUI that all the filter creation is done in. To start, create a new filter by clicking the "New" button in the top left of the window. You will be prompted to name your new filter. After clicking "ok," you can rename this at any time by simply clicking the name of your filter in the top left. To create a new subfilter for this filter, click the white "+" button to the right of your new filter. A new subfilter appears. To edit this subfilter, click the white arrow to the right of the subfilter. By doing so, a new "And" logic group appears on the right side of the window.

Similar to the official trade site, the Crafting Assistant supports "And," "Not," and "Count" filters. You can effectively make an "Or" logic type by creating a new subfilter. To create a new modifier in a logic group, click the white "+" button to the right of the respective logic type. To change the modifier, click on the "New Modifier" text. You will be prompted to select a valid modifier. After clicking "ok," you can then change the minimum and maximum values needed for this modifier to be counted as "hit." If this is not applicable to the modifier you are using, you may leave it blank and it will be ignored. To create a new logic group, simply click the white "+" button on the bottom right of the screen. To change the type of logic filter, click on the logic filter titlebar and change it accordingly. For count, you can change the minimum required hits by changing the value in the textbox.

## Running the Crafting Assistant
After you have created a valid filter, move the window to the right side of your monitor so that the item you will be crafting is visible. To begin, click the chaos orb icon in the Crafting Assistant window. It will have a green background if it is running. Now, start chaos spamming! The tool will play a sound to indicate if the item has hit the created filter. To turn off the assistant, simply click the chaos orb icon again.

## Saving and Opening Saved Filters
Filters are saved as you edit them automatically. They are saved to a subfolder in the "src" directory of the folder you downloaded. To quickly open this directory, click the "Folder" button on the top left of the tool's window. To open one of these filters, click the "Open" button. Filters are saved with the extension ".cbfilter" for easy use. They are not editable outside of the program.

## Creating a Shortcut to run the Assistant
Inside the downloaded folder, right click the .jar file and click "Create shortcut." Move this created file anywhere you want. Note you will need to recreate this shortcut if you move the folder in which the .jar is to run the Crafting Assistant.

## Editing the Delay
To edit the delay, click the gear on the bottom right of the screen. Personally, I run Path of Exile consistently at ~30 ms of ping and have the delay set to 50ms for best results. Adjust accordingly. This has not been officially tested at any higher or lower ping.

# Frequently Asked Questions

#### Will I be banned for using this tool?
No. This tool is **entirely** compliant with the Path of Exile terms of service for macros and 3rd party tools.

#### How do I create an "or" logic gate?
To create an "or" gate, you can create a subfilter in the GUI. To do so, click the white plus button on the left side of the screen to the right of your created filter. The tool will play the sound if the item hits **any** of the created subfilters.

#### Is this perfect?
No, it's not perfect. Error comes from network instability. For example, the tool will try to parse the item when the Path of Exile server has not responded yet, resulting in the same item being parsed twice. This error is being worked on. The tool also does not have every single modifier in the game.

#### Can I move the folder?
Yes! The tool is entirely stand-alone. However, the .jar and the "src" folder **must** remain in the same directory. Check above for steps in creating a shortcut to open the .jar.

# Roadmap
- Sandbox mode for testing filters on already crafted items
- Add map support (modifiers done. Need to add quantity: #% support).
- Possibly mac/linux support
- Bug: #% to all resistances is not supported

# Bug reports
If you find a bug, please create an issue thread. I will try to get back to you as soon as possible.

# Feature requests
Please feel free to send in any feature requests you may have.

