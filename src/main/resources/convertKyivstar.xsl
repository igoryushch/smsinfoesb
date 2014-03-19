<?xml version="1.0" encoding="UTF-8"?>
<!--
NovaPoshta
pampushko.o
06.03.2014
-->

<xsl:stylesheet version="1.0" 
xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
xmlns:xs="http://www.w3.org/2001/XMLSchema" 

extension-element-prefixes="date xs">
	<xsl:output method="xml" encoding="UTF-8" indent="yes"/>
	<xsl:template match="/">
		<root xmlns="http://goldentele.com/cpa">			
			<login>newmail</login>
			<paswd>sdgf232fsaqa2</paswd>
			<service>bulk-request</service>
			<tid>1</tid>			
			<messages>				
					<xsl:for-each select="smsRequestListWrapper/smsRequest">
						<message>
							<IDint>
								<xsl:value-of select="smsRequestId"/>
							</IDint>
							<sin>
								<xsl:value-of select="phoneNumber"/>
							</sin>
							<body content-type="text/plain">
								<xsl:value-of select="messageText"/>
							</body>
						</message>
					</xsl:for-each>				
			</messages>
		</root>
	</xsl:template>
</xsl:stylesheet>