# Function to check if a command exists
function CommandExists {
    param(
        [string]$command
    )

    $commandPath = Get-Command -Name $command -ErrorAction SilentlyContinue
    return ($commandPath -ne $null)
}

# Function to check the version of a command
function GetCommandVersion {
    param(
        [string]$command
    )

    $versionOutput = & $command --version 2>&1
    return $versionOutput
}

# Function to compare versions
function CompareVersions {
    param(
        [string]$installedVersion,
        [string]$requiredVersion
    )

    # Split version string by dots and take the first three components
    $installedVersionComponents = $installedVersion -split '\.' | Select-Object -First 3
    $requiredVersionComponents = $requiredVersion -split '\.' | Select-Object -First 3

    # Compare each component
    for ($i = 0; $i -lt 3; $i++) {
        $installedComponent = $installedVersionComponents[$i] -as [int]
        $requiredComponent = $requiredVersionComponents[$i] -as [int]

        if ($installedComponent -eq $null -or $requiredComponent -eq $null) {
            # If conversion to int fails, compare as strings
            $result = $installedVersionComponents[$i] -ge $requiredVersionComponents[$i]
        } else {
            # Compare as integers
            $result = $installedComponent -ge $requiredComponent
        }

        if (-not $result) {
            return $false
        }
    }

    # If all components are equal, consider versions equal
    return $true
}

# Check if Maven is installed
if (-not (CommandExists 'mvn')) {
    Write-Host "Maven is not installed. Please install Maven."
    exit 1
}

# Check Maven version
$mavenVersion = GetCommandVersion 'mvn'
$requiredMavenVersion = '3.8.6'
if (-not (CompareVersions $mavenVersion $requiredMavenVersion)) {
    Write-Host "Maven version $requiredMavenVersion or later is required. Please update Maven."
    exit 1
}

# Check if Docker is installed
if (-not (CommandExists 'docker')) {
    Write-Host "Docker is not installed. Please install Docker."
    exit 1
}

# Check Docker version
$dockerVersion = GetCommandVersion 'docker'
$requiredDockerVersion = '24.0.6'
if (-not (CompareVersions $dockerVersion $requiredDockerVersion)) {
    Write-Host "Docker version $requiredDockerVersion or later is required. Please update Docker."
    exit 1
}

# Check if Docker Compose is installed
if (-not (CommandExists 'docker-compose')) {
    Write-Host "Docker Compose is not installed. Please install Docker Compose."
    exit 1
}

# Check Docker Compose version
$dockerComposeVersion = GetCommandVersion 'docker-compose'
$requiredDockerComposeVersion = '2.22.0'
if (-not (CompareVersions $dockerComposeVersion $requiredDockerComposeVersion)) {
    Write-Host "Docker Compose version $requiredDockerComposeVersion or later is required. Please update Docker Compose."
    exit 1
}

# Check if npm is installed
if (-not (CommandExists 'npm')) {
    Write-Host "npm is not installed. Please install Node.js/npm."
    exit 1
}

# Check npm version
$npmVersion = GetCommandVersion 'npm'
$requiredNpmVersion = '9.5.1'
if (-not (CompareVersions $npmVersion $requiredNpmVersion)) {
    Write-Host "npm version $requiredNpmVersion or later is required. Please update npm."
    exit 1
}

# Check if Node.js is installed
if (-not (CommandExists 'node')) {
    Write-Host "Node.js is not installed. Please install Node.js."
    exit 1
}

# Check Node.js version
$nodeVersion = GetCommandVersion 'node'
$requiredNodeVersion = '18.16.1'
if (-not (CompareVersions $nodeVersion $requiredNodeVersion)) {
    Write-Host "Node.js version $requiredNodeVersion or later is required. Please update Node.js."
    exit 1
}

# Function to build Maven projects and create Docker containers
function BuildAndDockerize {
    param(
        [string]$directory,
        [string]$dockerImageName
    )

    # Navigate to the specified directory
    Set-Location -Path $directory

    # Run Maven clean package
    mvn clean package -DskipTests

    # Build Docker container
    docker build -t $dockerImageName .
    cd ..
}

# Build and Dockerize Maven projects
BuildAndDockerize -directory .\dragondestiny-discovery -dockerImageName dd_discovery:latest
BuildAndDockerize -directory .\dragondestiny-config-server -dockerImageName dd_config_server:latest
BuildAndDockerize -directory .\dragondestiny-engine -dockerImageName dd_engine:latest
BuildAndDockerize -directory .\dragondestiny-gateway -dockerImageName dd_gateway:latest
BuildAndDockerize -directory .\dragondestiny-played-game -dockerImageName dd_played_game:latest
BuildAndDockerize -directory .\dragondestiny-user -dockerImageName dd_user:latest

# Function to install npm dependencies, build Angular app, and create Docker container
function BuildAngularAndDockerize {
    param(
        [string]$directory,
        [string]$dockerImageName
    )

    # Navigate to the specified directory
    Set-Location -Path $directory

    # Run npm install
    npm install

    # Run npm build
    npm run build

    # Build Docker container
    docker build -t $dockerImageName .
    cd ..
    cd ..
}

# Build and Dockerize Angular app
BuildAngularAndDockerize -directory .\dragondestiny-ui\angular-app -dockerImageName dd_ui:latest
