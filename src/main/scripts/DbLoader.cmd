if exist createAll.sql del createAll.sql
:: Собираем файл createAll.sql из скриптовых нумерованых файлов.
for /f %%f in ('dir /b /O:N 0*.sql') do type %%f >>createAll.sql
pause