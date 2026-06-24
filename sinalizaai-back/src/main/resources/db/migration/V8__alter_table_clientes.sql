ALTER TABLE clientes
  ADD COLUMN cidade        VARCHAR(100)  NULL,
  ADD COLUMN pais          VARCHAR(100)  NULL DEFAULT 'Brasil',
  ADD COLUMN data_nascimento DATE         NULL;