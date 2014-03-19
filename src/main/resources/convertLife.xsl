<?xml version="1.0" encoding="UTF-8"?>
<!--
12.03.2014
-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs">
	<xsl:output method="xml" encoding="UTF-8" indent="yes"/>
	<xsl:template match="/">
		<message>
		<service id="individual" validity="+5 hour" source="NovaPoshta" uniq_key="1404141724"/>
			<xsl:for-each select="smsRequestListWrapper/smsRequest">
				<to>
					<xsl:variable name="phoneNumber"
					select="phoneNumber"/>
					<xsl:variable name = "lengthSt"
					select = "string-length($phoneNumber)" />
					<xsl:choose>
						<xsl:when test='$lengthSt=12'>
							<xsl:value-of select="$phoneNumber" />        
						</xsl:when>
						<xsl:otherwise>
                           <xsl:value-of select='concat("380", substring($phoneNumber, (number($lengthSt)-8), $lengthSt))' />
						</xsl:otherwise>
					</xsl:choose>	
				</to>
				
				<body content-type="text/plain">
					<xsl:value-of select="string(messageText)"/>
				</body>
			</xsl:for-each>
		</message>
	</xsl:template>
</xsl:stylesheet>