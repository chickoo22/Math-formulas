# MathVerse Play Store Release Runbook

## 1. Owner-supplied values

Before building a production bundle, obtain:

- AdMob app ID
- AdMob banner, interstitial, and rewarded ad-unit IDs
- A support email address
- A public HTTPS privacy-policy URL
- An Android upload keystore and passwords

Never commit the keystore, passwords, or production ad-unit IDs to Git.

## 2. Create an upload key

Run once and back up the resulting keystore safely:

```powershell
keytool -genkeypair -v -keystore mathverse-upload.jks -alias upload -keyalg RSA -keysize 2048 -validity 10000
```

## 3. Set release values for the current PowerShell session

```powershell
$env:KEYSTORE_PATH = "C:\secure\mathverse-upload.jks"
$env:STORE_PASSWORD = "your-store-password"
$env:KEY_PASSWORD = "your-key-password"
$env:ADMOB_APP_ID = "ca-app-pub-xxxxxxxxxxxxxxxx~yyyyyyyyyy"
$env:ADMOB_BANNER_AD_UNIT_ID = "ca-app-pub-xxxxxxxxxxxxxxxx/yyyyyyyyyy"
$env:ADMOB_INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-xxxxxxxxxxxxxxxx/yyyyyyyyyy"
$env:ADMOB_REWARDED_AD_UNIT_ID = "ca-app-pub-xxxxxxxxxxxxxxxx/yyyyyyyyyy"
```

The release build deliberately fails if any AdMob value is missing or still uses a Google test ID.

## 4. Validate and build

```powershell
./gradlew.bat :app:testDebugUnitTest --no-daemon
./gradlew.bat :app:bundleRelease --no-daemon
```

The release bundle is written to:

```text
app/build/outputs/bundle/release/app-release.aab
```

## 5. Final manual checks

- Test a release build on at least one physical phone.
- Confirm every core feature works without internet access.
- Confirm banner, rewarded, and interstitial ads use test units during testing and production units only in the signed release.
- Confirm quiz ads appear only at the configured optional/completion points.
- Verify the privacy-policy link, support email, and Play Store screenshots.
- Complete Play Console declarations: ads, Data Safety, content rating, target audience, and app access.
- Upload the `.aab` to an internal test track before production.
