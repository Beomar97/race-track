# 📋 Meeting Protocol

**Project/Meeting Name:** PSIT3-FS20-IT18ta_WIN-Team5 - Weekly Status Meeting

**Date of Meeting:** 13.03.2020, **Time:** 15:00 (GMT+1), **Location:** TB 510

## 👨🏼‍💻 Attendees

✅ Hans-Peter Hutter (Chair)

✅ Marco Forster (Project Manager)

✅ Dan Hochstrasser

✅ Manuel Berweger

✅ Marvin Tseng

## ✏️ Agenda

### Feedback Project Outline

- Table formatting needs to be updated.
- Some points in the **main use case** are not necessary, e.g. player exits the game, returns to the main menu etc.
- Game rules should be mentioned in the main use case.
- Additional requirements should hold system requirements and not game rules.
- Not possible to split development with an UML-Diagram, better to split tasks by use cases
- Use cases need to be presentable, something to show to the customer.
- Risks listed aren't really risks, risks can completely destroy the development of the game.
  - New rules/features can ruin the game by making it *unfun* -> test new rules and features on paper beforehand.
  - Current situation regarding the Coronavirus-Outbreak can be a risk.
  - How can you do against *missing knowledge*? Maybe specialise/differentiate the game from others.
- Use cases should be reviewed in general.
  - Use cases should give the customer a value.
  - Set priorities to use cases, which are critical and need to be implemented first?
  - Rough timeline should be updated with the right use cases (priority).
  - Some use cases can be combined (not really uses cases on their own).
  - Set game options can be multiple use cases (e.g. choose a track).
- Explain how the costs in the **Profitability** section got calculated.
- Only add project-specific terms and terms which are not googlable to the **Glossary**.

### Notes for next milestone (Solution Architecture)

- Elaboration phase (analysis).
- Find new additional requirements -> a few but good ones.

- FURPS.
  - Additional requirement for example: 7 Tracks -> not visible in use cases
- Domain Model.
  - Static view of the problem, shows important problems and concepts and needs to explain the problem without additional terms or explanations.
  - Provides candidates to be implemented as *classes* (Java) later.
  - It is not a UML-class diagram
  - Mention Paper with grid (Is part of the domain model and solution but not an own *class*)
  - Domain model and use cases are linked, must be consistent.
- System sequence diagram.
  - Shows the interaction between user and systems (DBs etc.)
- Desing interaction diagram.
  - Internal diagram which shows which classes from the system sequence diagram gets which actions and how they are being used/forwarded.
  - Class diagram can be derivated from it.
- Visualize use cases in a minimal version.
- Implementation.
- Project management.
  - New risk assessment needed.