import { z } from 'zod';
import { denominacoes } from './enums/churchEnums';

export const registerChurchSchema = z.object({
    cnpj: z.string().min(14, "CNPJ incompleto").max(18, "CNPJ inválido").nonempty("Campo obrigatório"),
    nome: z.string().nonempty("Campo obrigatório"),
    denominacao: z.enum(denominacoes, "Campo obrigatório"),
    outra_denominacao: z.string().optional(),
    endereco: z.object({
        cep: z.string().length(8, "CEP deve ter 8 dígitos").nonempty("Campo obrigatório"),
        uf: z.string().nonempty("Campo obrigatório"),
        cidade: z.string().nonempty("Campo obrigatório"),
        bairro: z.string().nonempty("Campo obrigatório"),
        rua: z.string().nonempty("Campo obrigatório"),
        numero: z.string().nullable().transform((val) => val.trim() === "" ? "S/N" : val).optional(),
        complemento: z.string().max(30, "O complemento deve ter no máximo 30 caracteres").nullable().optional(),
        endereco_principal: z.boolean().optional().default(false),
    }),
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

export type RegisterChurchFormData = z.infer<typeof registerChurchSchema>;