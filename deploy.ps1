$stoppedState = "Stopped"
$runningState = "Running"
$waitTime = "00:00:10" # Максимальное время ожидания запуска сервиса бд. Если время истекает, то ошибка.

$pgServiceName = "postgresql-x64-9.6"
$pgService = Get-Service $pgServiceName

if($pgService.Status -eq $stoppedState) {
    Write-Host "DB service is stopped. Running..." -ForegroundColor Green

    $pgService.Start()
    $pgService.WaitForStatus($runningState, $waitTime)
}

if($pgService.Status -eq $runningState) {
    Write-Host "Deploy is started..." -ForegroundColor Green

    mvn clean install tomcat7:run-war
}
else {
    Write-Host "Couldn't start DB service." -ForegroundColor Red
}