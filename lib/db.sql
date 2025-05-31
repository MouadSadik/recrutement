-- WARNING: This schema is for context only and is not meant to be run.
-- Table order and constraints may not be valid for execution.

CREATE TABLE public.abonnement (
  etat boolean DEFAULT true,
  codeclient integer,
  codejournal integer,
  datedebut date NOT NULL,
  dateexpiration date NOT NULL,
  idabonnement integer NOT NULL DEFAULT nextval('abonnement_idabonnement_seq'::regclass),
  CONSTRAINT abonnement_pkey PRIMARY KEY (idabonnement),
  CONSTRAINT abonnement_codeclient_fkey FOREIGN KEY (codeclient) REFERENCES public.entreprise(codeclient),
  CONSTRAINT abonnement_codejournal_fkey FOREIGN KEY (codejournal) REFERENCES public.journal(codejournal)
);
CREATE TABLE public.categoriejournal (
  libelle character varying,
  idcategorie integer NOT NULL DEFAULT nextval('categoriejournal_idcategorie_seq'::regclass),
  CONSTRAINT categoriejournal_pkey PRIMARY KEY (idcategorie)
);
CREATE TABLE public.client (
  adresse character varying,
  telephone character varying,
  codeclient integer NOT NULL DEFAULT nextval('client_codeclient_seq'::regclass),
  CONSTRAINT client_pkey PRIMARY KEY (codeclient)
);
CREATE TABLE public.demandeur (
  codeclient integer NOT NULL,
  nom character varying,
  prenom character varying,
  nbanneesexperience integer,
  salairesouhaite numeric,
  diplome character varying,
  CONSTRAINT demandeur_pkey PRIMARY KEY (codeclient),
  CONSTRAINT demandeur_codeclient_fkey FOREIGN KEY (codeclient) REFERENCES public.client(codeclient)
);
CREATE TABLE public.edition (
  codejournal integer NOT NULL,
  numedition integer NOT NULL,
  dateparution date,
  CONSTRAINT edition_pkey PRIMARY KEY (codejournal, numedition),
  CONSTRAINT edition_codejournal_fkey FOREIGN KEY (codejournal) REFERENCES public.journal(codejournal)
);
CREATE TABLE public.entreprise (
  codeclient integer NOT NULL,
  raisonsociale character varying,
  descriptionactivites text,
  CONSTRAINT entreprise_pkey PRIMARY KEY (codeclient),
  CONSTRAINT entreprise_codeclient_fkey FOREIGN KEY (codeclient) REFERENCES public.client(codeclient)
);
CREATE TABLE public.journal (
  nom character varying,
  periodicite character varying,
  langue character varying,
  idcategorie integer,
  codejournal integer NOT NULL DEFAULT nextval('journal_codejournal_seq'::regclass),
  CONSTRAINT journal_pkey PRIMARY KEY (codejournal),
  CONSTRAINT journal_idcategorie_fkey FOREIGN KEY (idcategorie) REFERENCES public.categoriejournal(idcategorie)
);
CREATE TABLE public.offreemploi (
  titre character varying,
  competences text,
  nbanneeexperiencedemandee integer,
  nbpostes integer,
  etat character varying CHECK (etat::text = ANY (ARRAY['ACTIVE'::character varying, 'DESACTIVEE'::character varying]::text[])),
  idabonnement integer,
  codejournal integer,
  numedition integer,
  numoffre integer NOT NULL DEFAULT nextval('offreemploi_numoffre_seq'::regclass),
  CONSTRAINT offreemploi_pkey PRIMARY KEY (numoffre),
  CONSTRAINT offreemploi_codejournal_numedition_fkey FOREIGN KEY (numedition) REFERENCES public.edition(numedition),
  CONSTRAINT offreemploi_codejournal_numedition_fkey FOREIGN KEY (codejournal) REFERENCES public.edition(numedition),
  CONSTRAINT offreemploi_codejournal_numedition_fkey FOREIGN KEY (numedition) REFERENCES public.edition(codejournal),
  CONSTRAINT offreemploi_codejournal_numedition_fkey FOREIGN KEY (codejournal) REFERENCES public.edition(codejournal),
  CONSTRAINT offreemploi_idabonnement_fkey FOREIGN KEY (idabonnement) REFERENCES public.abonnement(idabonnement)
);
CREATE TABLE public.postulation (
  codeclient integer,
  numoffre integer,
  codejournal integer,
  numedition integer,
  datepostulation date,
  idpostulation integer NOT NULL DEFAULT nextval('postulation_idpostulation_seq'::regclass),
  CONSTRAINT postulation_pkey PRIMARY KEY (idpostulation),
  CONSTRAINT postulation_codejournal_numedition_fkey FOREIGN KEY (numedition) REFERENCES public.edition(numedition),
  CONSTRAINT postulation_codejournal_numedition_fkey FOREIGN KEY (numedition) REFERENCES public.edition(codejournal),
  CONSTRAINT postulation_codeclient_fkey FOREIGN KEY (codeclient) REFERENCES public.demandeur(codeclient),
  CONSTRAINT postulation_numoffre_fkey FOREIGN KEY (numoffre) REFERENCES public.offreemploi(numoffre),
  CONSTRAINT postulation_codejournal_numedition_fkey FOREIGN KEY (codejournal) REFERENCES public.edition(codejournal),
  CONSTRAINT postulation_codejournal_numedition_fkey FOREIGN KEY (codejournal) REFERENCES public.edition(numedition)
);
CREATE TABLE public.preferencecategorie (
  codeclient integer NOT NULL,
  idcategorie integer NOT NULL,
  CONSTRAINT preferencecategorie_pkey PRIMARY KEY (codeclient, idcategorie),
  CONSTRAINT preferencecategorie_idcategorie_fkey FOREIGN KEY (idcategorie) REFERENCES public.categoriejournal(idcategorie),
  CONSTRAINT preferencecategorie_codeclient_fkey FOREIGN KEY (codeclient) REFERENCES public.client(codeclient)
);
CREATE TABLE public.recrutement (
  numoffre integer,
  codeclient integer,
  daterecrutement date,
  idrecrutement integer NOT NULL DEFAULT nextval('recrutement_idrecrutement_seq'::regclass),
  CONSTRAINT recrutement_pkey PRIMARY KEY (idrecrutement),
  CONSTRAINT recrutement_codeclient_fkey FOREIGN KEY (codeclient) REFERENCES public.demandeur(codeclient),
  CONSTRAINT recrutement_numoffre_fkey FOREIGN KEY (numoffre) REFERENCES public.offreemploi(numoffre)
);