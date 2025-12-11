import { useState, useEffect } from 'react';
import { getUser, getMemberRole } from '@/services/localCache';

/**
 * Hook para verificar o papel do usuário em um ministério
 * @param ministryId ID do ministério
 * @returns objeto com papel, loading e funções de verificação
 */
export const useMinistryRole = (ministryId: string | null) => {
  const [role, setRole] = useState<'lider' | 'ministro' | 'levita' | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    if (!ministryId) {
      setRole(null);
      setIsLoading(false);
      return;
    }

    const checkRole = async () => {
      try {
        setIsLoading(true);
        const user = await getUser();
        
        if (!user) {
          setRole(null);
          return;
        }

        const userRole = await getMemberRole(user.id, ministryId);
        setRole(userRole);
      } catch (error) {
        console.error('❌ Erro ao verificar papel do usuário:', error);
        setRole(null);
      } finally {
        setIsLoading(false);
      }
    };

    checkRole();
  }, [ministryId]);

  return {
    role,
    isLoading,
    isLeader: role === 'lider',
    isMinistro: role === 'ministro',
    isLevita: role === 'levita',
    hasRole: role !== null,
  };
};
