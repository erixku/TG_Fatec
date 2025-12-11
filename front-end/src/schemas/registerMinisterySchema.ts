import { z } from 'zod';

export const registerMinisterySchema = z.object({
  nome: z.string()
    .min(3, "Nome do ministério deve ter no mínimo 3 caracteres")
    .max(100, "Nome do ministério muito longo"),
  
  descricao: z.string()
    .min(10, "Descrição deve ter no mínimo 10 caracteres")
    .max(500, "Descrição muito longa"),
  
  arquivo: z.object({
    caminho: z.string().nullable(),
    tipoArquivo: z.string().nullable(),
    mimeType: z.string().nullable(),
    tamanhoEmBytes: z.string().nullable(),
    bucketArquivo: z.object({
      nome: z.string().nullable()
    })
  }).optional()
});

export type RegisterMinisteryFormData = z.infer<typeof registerMinisterySchema>;

