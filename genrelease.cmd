cd /CB/dev/PoE/CraftingBot
rd CraftingBot /s /q
md CraftingBot
cd CraftingBot
md src
cd src
xcopy \CB\dev\PoE\CraftingBot\src /e
rd test /s /q
cd main
rd java /s /q
cd /CB/dev/PoE/CraftingBot/CraftingBot
copy C:\CB\dev\PoE\CraftingBot\target\CraftingBot_0.1-1.0-SNAPSHOT-jar-with-dependencies.jar
copy C:\CB\dev\PoE\CraftingBot\README.md
cd /CB/dev/PoE/CraftingBot/CraftingBot/src/main/resources
del /f /q settings.cbsettings
rd filters /s /q
md filters

