if exist createAll.sql del createAll.sql
for /f %%f in ('dir /b /O:N 0*.sql') do type %%f >>createAll.sql
pause