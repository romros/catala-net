### Context:

**Projecte:** CatalàNet Tracker

**Descripció:**
Agent de monitorització per a registrar i analitzar la prevalença del català en cercadors i llocs d'Internet. L'objectiu és seguir l'evolució de la seva presència al llarg del temps.

### Resum:

**Projecte**: CatalàNet Tracker

**Objectiu**: Monitoritzar la prevalença del català en cercadors i llocs d'Internet per analitzar la seva evolució al llarg del temps.

**Estructura**:

- Projecte Maven amb estructura multimòdul: POM pare amb mòduls fills.
- **POM Pare** ('catala-net-tracker'):
  - Dependencies bàsiques de Spring Boot i Java 17.
  - Mòduls: 'core' i 'search-module'.
- **Mòdul 'core'** ('core-db-agent'):
  - Funcionalitats centrals com la gestió de la base de dades.
  - Dependencies com JSoup, JPA, HSQLDB, i oshi-core.
- **Mòdul 'search-module'**:
  - Realitza i gestiona les cerques sobre la presència del català.
  - Dependencies com JSoup, JPA, Lingua, i Jackson.

Aquesta es l'esctructura actual, i ara estem creant el mòdul:

- Mòdul scheduler:
  - Gestiona les tasques programades i les executa.

**Estratègia**:

1. Programar una tasca en el Task Scheduler de Windows: s'executa a les 9h i després cada hora. Només crida al modul scheduler,
   i el modul schedules és el responsable de en el fons executar les tasques i tenir un sistema de jobs i tasks que es farà amb spring-boot o amb el millor
   sistema de tasques que creguis.
2. El programa principal revisa:

- Si la tasca del Task Scheduler existeix.
- Revisa les tasques pendents.
- Executa la següent tasca i l'elimina de la llista.

3. Les tasques poden incloure:

- Comprovar configuració, fer cerques a Google, enviar dades/estat a central, mirar actualitzacions, instal·lar actualitzacions, revisar qualitat de dades, netejar dades antigues, reiniciar components, etc.

4. Persistència:
   - ús de Spring Data JPA amb alguna base de dades SQL (HSQLDB, MySQL, PostgreSQL). Usarem el mes simple
   - Crea entitats per a Tasques, Execucions de Tasques, i Resultats d'Execució. Això permetrà un seguiment detallat de quan es va programar una tasca, quan es va executar i quin va ser el resultat.
5. Logs i Monitoratge: Implementa un sistema de logs robust amb eines com Logback o SLF4J. Això facilitarà la identificació de problemes o incidències.
   Considera la incorporació d'eines de monitoratge com Spring Boot Actuator per a obtenir informació sobre l'estat i la salut de l'aplicació.

**Pregunta**:
Si us plau fes un prototip d'aquest modul que posi tot el que necessiti per a funcionar, i que faci una tasca de prova que es pugui veure que funciona.
Si es necessiten stubs creem els mínims i posem todo en les funcions. recorda que les tasques qeu faran son: Comprovar configuració en el servidor remot, fer cerques a Google segons la configuracio remota, enviar dades/estat a central, mirar actualitzacions, instal·lar actualitzacions, revisar qualitat de dades, netejar dades antigues, reiniciar components, etc.
