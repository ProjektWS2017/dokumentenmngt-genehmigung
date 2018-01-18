# Der BPM Prozess im Dokumentenmanagement System zur Rechnungsfreigabe

## Der Prozess im Camunda Modeller

Der Prozess wurde in Camunda modelliert.
In unserem Dokumenten Management System gibt es den Prozess für die Rechnungsfreigabe.
Rechnungen sind Mitarbeitergruppen zugeordnet und haben einen Rechnungsbetrag.
Der Prozess im Dokumentenmanagement System sieht für diesen Prozess wie folgt aus.

![BPMN Prozess](https://lh6.googleusercontent.com/9wuYTo9RGWv4L_YIIYld_keVKktLVVGOjDwLdBsGm24R4QrRIYpOLUd_nSCjNYB3k-QAj_b0ZYTPXbgw35PkseGmbfFBk0jULfzZKhpQkMzqY0wqXmpgelbChCciVTT_QIHRPK6_)

Die Rechnungen werden in der Fachabteilung von Mitarbeitern erstellt, die die Verträge aushandeln.
Die Rechnungen werden im Dokumentenmanagement System dann in den Rechnungsfreigabeprozess aufgenommen und die Parameter eingegeben. 
Dieser Service prüft mit der DMN Tabelle die Eingabewerte und genehmigt die Rechnung entweder automatisch und leitet die Informationen über eine nexternen Service an das Rechnungswesen weiter, oder leitet diese an den entsprechenden Vorgesetzten weiter.


In der DMN Entscheidungstabelle wird geprüft in welcher Mitarbeitergruppe der Mitarbeiter eingeteilt ist und welchen Betrag die Rechnung hat.
Durch diese Werte wird die Rechnung entweder automatisch genehmigt oder an den entsprechenden Vorgesetzten weitergeleitet, der die Rechnung im Prozess freigeben muss.



In der ersten Aktivität kann der Mitarbeiter in einer Eingabemaske die Parameter für die Mitarbeitergruppe und den Rechnungsbetrag eingegeben.
Es werden über eine Form der Rechnungsbetrag und die Mitarbeitergruppe erfasst:

![Eingabedaten](https://lh6.googleusercontent.com/P61YSwPs15HxTZSYxo-UQhMfgyxjIIEix509KAdVLXTUwJiITCKfOdMOsJc7mDzG_v8yqgVfl96WztXLN6Vm_z5ED0HIfX-I2jdf-B9Hal4tgOk4zRziS3BdpLsYVMg4HXocRQ94)

Die Daten werden diese durch eine Entscheidungstabelle verarbeitet:

![Entscheidung](https://lh5.googleusercontent.com/JpDQ1Qo302k516K4B-4flxTWRmhj6eZ1JH2NhD-oI6LSjSMeeLRCgf_1XSMQVUMyxSsPMK1ObLLw6FrgG8QcUBPiM2bYczGohtGjvGcG-aqc-s-xfj9EXMETxWfmSQUpOZNW7gPT)


Die BPM Aktivität für die Entscheidungstabelle sieht im XML wie folgt aus.
hier ist auch die result variable definiert:
```
    <bpmn:businessRuleTask id="dmnTaskRechnungsPruefung" name="Rechnungs Informationen prüfen" 
 camunda:decisionRef="BerechtigungsMatrix"
        camunda:mapDecisionResult="singleEntry"
        camunda:resultVariable="result" >
      <bpmn:incoming>SequenceFlow_0r08tj1</bpmn:incoming>
      <bpmn:outgoing>SequenceFlow_097nb1v</bpmn:outgoing>
    </bpmn:businessRuleTask>
```
Hier gibt es den Parameter decisionRef mit dem Verweis auf unsere Entscheidungstabelle und den Parameter resultVariable der das Ergebnis aus der Entscheidungstabelle bekommt.
Die Entscheidungstabelle hat als Ausgang ein result, das sich durch die Parameter aus der Mitarbeiter Form der Mitarbeitergruppe und dem Rechnungsbetrag entscheidet.
In der BPM Regel Aktivität haben wir das DMN mit der ID “BerechtigungsMatrix” verlinkt, diese ID geben wir nun unserer DMN Entscheidungstabelle:

![ETab](https://lh4.googleusercontent.com/Mqkn2indEdQOEc7-FsDjwqiEu4CYIdc1FMjFg5ANC48Q8hqmFHSV3O4gQC0NnGJ_ObSDE0mdjwYFNrjypIT6CICpiefyM2qsnb7GO_p9hJtrOBopTsQNNWIQug6TTOLilVuYCAD3)

In der Camunda Dokumentation zur Integration von DMN Entscheidungstabellen in den BPM Prozess
https://docs.camunda.org/manual/develop/user-guide/process-engine/decisions/bpmn-cmmn
steht dann, dass die result variable so benutzt werden kann:
```
Object value = decisionResult
  	.getSingleResult()
  	.getEntry("result");
```
Das Result wird dann im Gateway ausgewertet:

![Gateway](https://lh5.googleusercontent.com/mgh-Nqz17JsSVZ_d8to5dhqJzyeSo2URDKWjqLaDKaAexXmKpJ8CwheuxDMV8OoZ6B_9ytxbKKCQfK28vLG-forPdAH83Wtmj1WfUGXPXEfo0WuSrUSnFwL9BidP0sDygiDUEuwV)



## Mit Eclipse und Maven zum ausführbaren Prozess

Zur Realisierung wird die BPM Engine von Camunda verwendet, welche Möglichkeiten der BPM 2.0 Spezifikation benutzt. Das erstellte BPM wird in Eclipse importiert und es werden die Camunda Bibliotheken als Plugin installiert und dann können wir die Lane aus dem Prozessmodell ausführbar einstellen und über die Programmiersprache Java Services aufrufen und automatische Logik in den Prozess integrieren.

In Eclipse erstellen wird dazu ein Maven Projekt:

![EC1](https://lh4.googleusercontent.com/Wc4XssYeKhAAwGS6WgWEY7xFsCRfSBBNGcEkCqPonjHAbhL8ngjc_hwjrrgdQ5-y-hEIOQbQkydFsz-M79AE_t7HjZsR8MLEyy_XmUnkMhz5M2Jq7u27RbcsQngM41guD90lTdVR)

und nutzen die Camunda Plugins um die Entsprechende Projektstruktur automatisch zu erstellen:

![EC2](https://lh6.googleusercontent.com/IpRmohPNIHj__E3uEYzU8KYHAp_blH-IsESByQKnuPHi9faqOihE6Ja61X50tv9G1hX_GKtcr7ehaN6G-QWvwH9lhf6YDya5Z7bbjttbjCw0m6yG5mPZBvRi7gRfLAbpVNMD4kwf)

![EC3](https://lh6.googleusercontent.com/Yc3AHgLTdEKVOLY4Lu0WMyl9PZPKi0AZksNNZixT-G7_94eDORyNxN1SRIa8fD-S8W88cx_K3KLmOhUVQ7tOn6G9uF8gkZV6J4ozzWG-bc2PbwgVmb8f-XXLs3amMkM6DrpVDwW5)

Die Camunda Maven Archetypes können hier bezogen werden, diese können dann in Eclipse als Software installiert werden:
https://docs.camunda.org/manual/7.4/user-guide/process-applications/maven-archetypes/

Dadurch wird die Projektstruktur automatisch erstellt und wir können im nächsten Schritt unser BPM aus Camunda importieren:

![EC4](https://lh5.googleusercontent.com/K5yCmAfHCtxSKAn7lzLwxu7VKXbGDvm_GOb9NZGlL5bv9AgSnlPo4mXolKCTMOI0nbLlzXUFFHUJcQVbKHYP6rHVhIT2mVUqldnQ8xiQfd1Q48mOaBgs4ApxvURnoeWaMo7-5znu)


Nun kommen die Eclipse spezifischen Einstellungen zum Tragen, durch die wir die Lane automatisch ausführbar machen.

![EC5](https://lh5.googleusercontent.com/mtaJx2m-c-XT5cDGEmITf3fwDWfq1bRDQHjB0PAECiOvrkfXBHX1yv9WnV7UlDiuNNmxgi64t6yp5dRcSCt2VBkqR7PVVhvZx2LfKlfzijiDFZFHlYVNS-NzFKZozuJwoJT7BJIA)

Die Lane wird als “ausführbar” markiert und die Prozess ID ist wichtig, da wir diese nun im Java Code benutzen, um eine Instanz der prozess Engine zu erstellen.

Prozess ID: ProcessRechnungsFreigabe
BPM Datei: bpmnDokumentenmanagement.bpmn

Diese Informationen werden benutzt um nun in einem Test zu schauen, ob das BPM okay ist und eine Prozess Engine daraus erstellt werden kann. In Eclipse wird ein sogenannter Unit Test ausgeführt, der auf das BPM verweist und unsere Prozess Id benutzt um die Engine zu erstellen.


Darüber hinaus müssen allen Service Aktivitäten im Prozess direkte Java Klassen zugeordnet werden, die dann bei der Aktivität aufgerufen werden und Java Logik ausführen können.

![EC6](https://lh5.googleusercontent.com/sJ-0WpWPOIRCF1fGxFKvc3qVVbg56hqR5naJwZicrOuiZZnEntQ96UrgVOLGrcbazdjLv6eenPactfxWW3ObVAq73dOdfhP0ZbcUS9uEKMAkC5D86Fa0Jyzfifzx406kmEIlRrud)


Die verknüpfte Klasse muss die Camunda Bibliotheken importieren, von der Camunda Klasse JavaDelegate erben und die “public void” Methode execute mit dem Parameter für die Ausführung haben:

![EC7](https://lh4.googleusercontent.com/MhgXZhN8T7RZWqX7WwPlZipWWFUx27lP28jz1GFSnJ6BEs0P824e91sKyP7Yhu8-Fzu1d8b0pzimTIs-rPi32A-v3f_jY5O_JcPLjVNv9Sx2VfT_IgsDcdQKwYcHUkf6tWxjdYkw)

Die Methode wird dann von der Prozess Engine aufgerufen.



Für unsere Tests benutzen wir die Java Unit Test Methoden, die prüfen, ob alles okay ist.

![EC8](https://lh3.googleusercontent.com/I7z0XtfmjSr9N8k7btSqQ327YaQSeMP2tsgGq07zc_BtMe082HuA-HKKeKqZHQl9RhWN1qTkrOEENRAkL0__8_U4b2USlLO_kFiKMc27jciYEkyqbWp4EltBCcclv8CwNTcfJYws)

Nachdem das BPM entsprechend angepasst wurde und die Java Klassen hinterlegt sind, kann der Unit test gestartet werden, um zu prüfen ob das deployen funktioniert.
Wenn die Tests okay sind, kann das Maven Projekt deployed werden:

![EC9](https://lh5.googleusercontent.com/cO5jQzYj7rpCJ-4KupftYpCJHA5lrCswWY_d9JzsLfcEDdmnK_Sj9wr8_PDypWIRBBTT1j4GeBLgGmYFlz91LY4oXYFHSDlTAqH7PGRp3n8t_0HViyvLfbhxH_sZcaJFIc8kp53N)

Beim Deployed wird der Test durchgeführt:

![EC10](https://lh5.googleusercontent.com/yeMKWac0ZtzDVW6E0f9ncR509pgM3HXooiFrzVbJO6G5NVIr0hjb-oMOTcd46mcGgz6Qnl7uOuyqxz_S95UBK7uLPG4qdQy_-It6mcL0ZkVJJeb1N9KvNGeNS50VD8pf_GKA_7hC)

Und die WAR Datei für den Server erstellt:

![EC11](https://lh5.googleusercontent.com/_PuhrLKXiDqxzL5CQWZnL-QsZrCmCKmIiaxAwFg8SoWUdjjyiml8M7sBnWr-HfCAeGpRAbpPVDy8ukUptM54fdY-xgO-iKkFV6pMM-amRnifvMoJQ37W8xZdpBIFaYfamlyZ21Ls)



## Camunda BPM Plattform

Um lokal unseren BPM Prozess und die Entscheidungstabelle zu testen ,habe wir die Camunda Plattform geladen:
https://camunda.org/download/

Webserver mit Camunda Plattform starten:
```
~/Camunda/tomCat $ ./start-camunda.sh 
starting camunda BPM platform on Tomcat Application Server
Using CATALINA_BASE:   ./server/apache-tomcat-8.0.47
Using CATALINA_HOME:   ./server/apache-tomcat-8.0.47
Using CATALINA_TMPDIR: ./server/apache-tomcat-8.0.47/temp
Using JRE_HOME:        /usr/lib/jvm/java-8-oracle
Using CLASSPATH:       ./server/apache-tomcat-8.0.47/bin/bootstrap.jar:./server/apache-tomcat-8.0.47/bin/tomcat-juli.jar
Tomcat started.
```

Und lokal läuft nun die Camunda Plattform zum Testen:

![CAMP1](https://lh5.googleusercontent.com/Nc9p9sB8dWyivdogYjCIAT9IbB7CVhvhdXTYN40NjhRRmOxliVhb_qM8No8GN9v-mdgCtIcBWpq5SHhx-KGJHkvHVFaW0-AgIijqjCy-G1eJN97bJEAEtb1NULrHyXX1uGjf_Twf)

Hier können wir das Eclipse Projekt importieren:

![CAMP2](https://lh4.googleusercontent.com/yLHflabs1i1E35M9mI7qY1FBi1gZjIEZ8JvdKpwFvzjyE7dkIAXO8m5XDNjCVtcNcmvSpINGz4CtwzbyBClSrBowHnERWnOTqSJlK4dalaVZwchm1blKh6bReMyiWdjaWeyahNji)

In der Taskliste kann nun der Prozess gestartet werden:

![CAMP3](https://lh5.googleusercontent.com/zJJs4anBBjKWCgyI6yxhW2Vg_2FNoA3Xc1h10wXxZYTO0aqsK0hmt2Plhkcl_suw8Dt2XGbwm18zGT2CG9cCHc5leA8vWy_Yf7NIXdJck6fLFvYng8QEyCILWO3Y1o5z79C76BwY)

Und dadurch werden Instances zu den Prozess aktiv:

![CAMP4](https://lh6.googleusercontent.com/S9uTRJAXKbmL5BEXaT2RLjONkqRECBkMCeGqQWmuhtI-UYDdWnkW03I1p2kUnrTmEgDnV8stq6tRejeQ4z74Tg3eFL-KO_3-Dw-XGrVZF5pumPlhsXwUMSLaG0zE5t5IBh-VDg3R)



### Camunda EMail aus einer Aktivität


Ist der Rechnungsbetrag für die jeweilige Mitarbeitergruppe zu hoch, wird an den entsprechenden Vorgesetzten eine EMail gesandt.

Zum EMail Versandt, wird eine Java Klasse mit der Aktivität verknüpft und in der Klasse die Funktionalität realisiert.
Wir verknüpfen:

com.thbrandenburg.camunda.meister.docmanagement.genehmigung.RechnungAnVorgesetztenA

Das Versenden an sich benötigt einen EMail Server oder einen Web Service aufruf, um mit einem Webmail Account eine EMail zu verschicken.
Lösung Dokumentiert:

https://javaee.github.io/javamail/
https://stackoverflow.com/questions/31392100/eclipse-java-send-email-from-gmail-smtp-programatically


![MAILA](https://lh5.googleusercontent.com/CPVgBcXxvEj42N6eBvVMkvs_F-6cpZHn3fdE2HDN4UqL38M7XVAdBxPZcOvvTfQhqdrdgPlKKJPUHV4Vsj1Mt6UeX-ufe2QzFEKgbGZtTYR-fYTTlO1njQ3gWNoZ92LoM3WrfIU5)


