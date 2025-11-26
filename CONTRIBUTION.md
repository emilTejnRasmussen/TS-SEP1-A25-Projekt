# Contributing Guide

Denne guide beskriver, hvordan vi arbejder med Git og GitHub i projektet.

---

## Første gang

1. Klon repository:

   ```bash
   git clone https://github.com/emilTejnRasmussen/TS-SEP1-A25-Projekt.git
   ```
2. Gå ind i projektmappen:

   ```bash
   cd <folder-name>
   ```

---

## Hver gang inden du begynder at arbejde

1. Skift til main:

   ```bash
   git checkout main
   ```
2. Hent den nyeste version:

   ```bash
   git pull
   ```

Du har nu den nyeste version af projektet.

---

## Start en ny feature

1. Opret en ny branch:

   ```bash
   git checkout -b feature/<name>-<task>
   ```

   **Eksempel:**

   ```bash
   git checkout -b feature/emil-add-resident-ui
   ```

2. Lav dine ændringer.

3. Hver gang du har lavet noget konkret:

   ```bash
   git add .
   git commit -m "Kort beskrivelse af ændringen"
   ```

   *Commit ofte.*

4. Push din branch til GitHub:

   ```bash
   git push -u origin feature/<name>-<task>
   ```

Hvis du er i tvivl om hvilken branch du er på:

```bash
git branch
```

---

## Lav et Pull Request (PR)

1. Opret et PR på GitHub
2. Tilføj mindst én reviewer
3. Skriv en kort beskrivelse af din feature

---

## Review og merge

Når PR'en er godkendt:

1. Merge til `main`
2. Slet feature-branchen på GitHub

For at slette branchen lokalt:

```bash
git branch -d feature/<name>-<task>
```

Tjek dine lokale branches:

```bash
git branch
```

Du bør kun have `main`, medmindre du arbejder på en ny feature.

---

## Efter en PR er blevet merged

1. Skift tilbage til main:

   ```bash
   git checkout main
   ```
2. Hent nyeste version:

   ```bash
   git pull
   ```

---

## Fejl: "There is no tracking information for the current branch"

Hvis du får denne fejl ved `git pull`:

```
There is no tracking information for the current branch.
```

Så følger din lokale `main` ikke den rigtige remote branch.

### Løsning

1. Sikr dig, at du er på main:

   ```bash
   git checkout main
   git branch
   ```

2. Sæt tracking korrekt:

   ```bash
   git branch --set-upstream-to=origin/main main
   ```

Nu virker:

```bash
git pull
```

---

Har du spørgsmål til workflowet? Spørg i gruppen!
