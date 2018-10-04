SELECT 
	QUOTENAME(CONVERT(VARCHAR(MAX), [ITEMID]), CHAR(34)) AS ApplicationGUID,
	QUOTENAME(app.[Name],CHAR(34)) AS ApplicationName,
	QUOTENAME(app.[Short Name],CHAR(34)) AS ApplicationShortName
FROM
	[IT: Application] app
ORDER BY
	ApplicationName ASC
