INSERT INTO public.specialtys (code, name) VALUES ('CARD', 'Cardiología')
    ON CONFLICT (code) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialtys (code, name) VALUES ('NEUR', 'Neurología')
    ON CONFLICT (code) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialtys (code, name) VALUES ('ONCO', 'Oncología')
    ON CONFLICT (code) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialtys (code, name) VALUES ('PEDI', 'Pediatría')
    ON CONFLICT (code) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialtys (code, name) VALUES ('DERM', 'Dermatología')
    ON CONFLICT (code) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialtys (code, name) VALUES ('ENDO', 'Endocrinología')
    ON CONFLICT (code) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialtys (code, name) VALUES ('GAST', 'Gastroenterología')
    ON CONFLICT (code) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialtys (code, name) VALUES ('GINE', 'Ginecología')
    ON CONFLICT (code) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialtys (code, name) VALUES ('HEMA', 'Hematología')
    ON CONFLICT (code) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialtys (code, name) VALUES ('INMU', 'Inmunología')
    ON CONFLICT (code) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialtys (code, name) VALUES ('NEFR', 'Nefrología')
    ON CONFLICT (code) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialtys (code, name) VALUES ('NEUM', 'Neumología')
    ON CONFLICT (code) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialtys (code, name) VALUES ('OFAL', 'Oftalmología')
    ON CONFLICT (code) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialtys (code, name) VALUES ('ORTO', 'Ortopedia')
    ON CONFLICT (code) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialtys (code, name) VALUES ('OTOR', 'Otorrinolaringología')
    ON CONFLICT (code) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialtys (code, name) VALUES ('PSIQ', 'Psiquiatría')
    ON CONFLICT (code) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialtys (code, name) VALUES ('REUM', 'Reumatología')
    ON CONFLICT (code) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialtys (code, name) VALUES ('UROL', 'Urología')
    ON CONFLICT (code) DO UPDATE SET name = excluded.name;