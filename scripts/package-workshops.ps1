# Create four independently usable source archives. Run from any directory.
$ErrorActionPreference = 'Stop'
$repoRoot = Split-Path -Parent $PSScriptRoot
$licensingNotice = Join-Path $repoRoot 'LICENSING.md'
if (-not (Test-Path -LiteralPath $licensingNotice)) {
    throw "Missing course licensing notice: $licensingNotice"
}
$archiveDirectory = Join-Path $repoRoot 'target/workshop-archives'
New-Item -ItemType Directory -Force -Path $archiveDirectory | Out-Null

foreach ($number in 1..4) {
    $checkpointName = 'workshop-{0:D2}' -f $number
    $checkpointDirectory = Join-Path $repoRoot "workshops/$checkpointName"
    $entries = @('pom.xml', 'README.md', 'ASSET_LICENSE.md', '.gitignore', 'src') |
        ForEach-Object {
            $entry = Join-Path $checkpointDirectory $_
            if (-not (Test-Path -LiteralPath $entry)) {
                throw "Missing checkpoint file or directory: $entry"
            }
            $entry
        }
    $entries += $licensingNotice
    $archivePath = Join-Path $archiveDirectory "kcl-tech-java-$checkpointName.zip"
    Compress-Archive -LiteralPath $entries -DestinationPath $archivePath -Force
    Write-Output $archivePath
}
