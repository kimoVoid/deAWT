# deAWT mod for ornithe
Gets rid of the usage of AWT Frame/Canvas, which the game uses in legacy versions.
<br>This mod was made and tested on Beta 1.7.3, it might work with earlier betas and even up to release 1.2.5. Feel free to port this to more versions if you want.
<br>
<br>You can grab the installer for Ornithe on [their website!](https://ornithemc.net/) You can also generate MultiMC/Prism instances with it.

## Notable changes
- Restores the icon and title, which can now be changed with `org.lwjgl.opengl.Display#setTitle()` and `org.lwjgl.opengl.Display#setIcon()`. The stock icon is set to the legacy grass block.
- Re-introduces the "Quit Game" button.
- Mouse cursor is properly centered on fullscreen.
- Also added Apple M chips support, fixing the inverted colors issue.
- Fixes the game not scaling properly when using high DPI.

## Credits
This mod was heavily inspired by Gambac for Babric, made by DanyGames2014.
<br>🔗 https://modrinth.com/mod/gambac
