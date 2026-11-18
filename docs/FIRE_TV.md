# Fire TV / Android TV support

Xtra runs on Amazon Fire TV sticks and boxes and on Android TV / Google TV devices.
It is detected automatically; if detection fails, enable **Force TV mode** in
Settings → User interface → Force TV mode.

## Sideload the APK with the Downloader app

1. On the Fire TV home screen go to **Find → Search** and type **Downloader**
   (the orange icon by AFTVnews). Install it.
2. Open **Settings → My Fire TV → Developer Options** and turn on
   **Apps from Unknown Sources** (on newer Fire OS: **Install Unknown Apps**,
   then allow **Downloader**).
   On Google TV: **Settings → Apps → Security & Restrictions → Unknown sources**.
3. Open **Downloader** and enter the APK URL:
   - CI build: download the `xtra-debug-apk` artifact from the
     [Actions tab](https://github.com/subhashherur/Xtra/actions) (GitHub requires
     being logged in to download artifacts; unzip `xtra-debug-apk.zip` to get
     `app-debug.apk`, then host it somewhere reachable, e.g. a GitHub Gist or
     your own server), or
   - any other APK you built yourself with `./gradlew assembleDebug`.
4. After the download finishes, Downloader asks to install — confirm.
   The app appears as **Xtra** on the home row / in **Your apps & channels → See all**.

Updates: repeat the same steps with a newer APK; the package installs over the
existing one (data is kept).

Remote tip: the Downloader URL field accepts a short host name if you use a
URL-shortener for long links.

## Controls (Fire TV remote)

| Remote button | On screens (grid, lists, settings) | In the player | In chat |
|---|---|---|---|
| D-pad | Move focus between items, tabs and the bottom bar | Move focus between control buttons | Move through messages / input row |
| Select (middle) | Open item / toggle switch / open keyboard in search | Play/pause. When a control is focused, it activates that control | Focus the chat input → opens the keyboard; on the input row, sends the message |
| Back | Go back one screen; exit the app from the top level | Hide the controls first, then minimize the player, then exit | Close keyboard / close menus |
| D-pad Up in the player | — | Show the controls (focus lands on the seek bar) | — |
| D-pad Down in the player | — | Hide the controls | — |
| D-pad Left/Right in the player | — | Seek back/forward (10 s on VODs by default, configurable in player settings; DVR window on live streams). Same seek steps when the seek bar is focused | — |
| Play/Pause | — | Play/pause | — |
| Rewind / Fast-forward | — | Seek back/forward (VODs) | — |

Notes:
- Chat is shown **beside the video** in TV mode by default. Toggle it with the
  chat button in the player controls.
- The on-screen keyboard opens automatically when Select is pressed on a text
  field (search box, chat input).
- Lists keep their position; the **back** key always exits cleanly.

## Settings that are useful on a TV

- **Settings → User interface → Force TV mode** – enables TV behaviour on
  devices that are not detected as TVs (also useful for testing on phones).
- **Settings → Chat → Chat width** – width of the chat panel next to the video.
- **Settings → Player → Rewind/forward durations** – seek steps for
  Left/Right and the rewind buttons.
- Column count on the home grid follows the landscape column setting; TV mode
  defaults to 3 columns unless you set a custom count.

## Building for TV yourself

```bash
git clone -b fire-tv https://github.com/subhashherur/Xtra
cd Xtra && ./gradlew assembleDebug
# app/build/outputs/apk/debug/app-debug.apk
```

Every push to the `fire-tv` branch produces a debug APK artifact in GitHub
Actions.

## Known limitations

- **Login**: the Twitch OAuth login screen is shown in a WebView. It works on
  Fire TV but is designed for a mouse; use the D-pad to select fields, and if
  the keyboard does not appear on a field, press Select again. Copying the
  login code and finishing on a phone is often easier.
- **Chat input requires an account and the Leanback/Google keyboard**; on
  Fire TV the built-in keyboard is used. Emote picker works with the D-pad.
- **7TV/BTTV/FFZ emotes**: 7TV channel emotes are loaded through 7TV's v3
  GraphQL API (the old REST users endpoint was removed by 7TV and no longer
  works). Global 7TV emotes, BTTV and FFZ are unaffected. Personal 7TV emotes
  and live 7TV emote updates need the account to be connected to 7TV.
- The picture-in-picture and background-audio phone features are not exposed
  on TV (no UI to enter them).
- Some rarely used screens (downloads, proxy settings) are reachable with the
  remote but were designed for touch; they are usable but not pretty.
- On Android TV emulators without a real Twitch login, some streams with
  ads/integrity restrictions may fail to start; this is not TV-specific.
