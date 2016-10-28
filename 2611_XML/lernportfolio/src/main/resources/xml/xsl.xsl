<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
   
    <xsl:template match="/">
            <xsl:apply-templates select="html"/>
    </xsl:template>
    
    <xsl:template match="html">
        <xsl:variable name="elementName" select="container/@name"/>
        <xsl:element name="{$elementName}">
            <xsl:apply-templates select="container"/>
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="container">
        <xsl:variable name="elementName" select="container/@name"/>
        <xsl:element name="{$elementName}">
            <xsl:apply-templates select="container/@name"/>
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="container/@name">
        <xsl:variable name="elementName" select="container/@name"/>
        <xsl:element name="{$elementName}">
            <xsl:apply-templates select="container/@name"/>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>