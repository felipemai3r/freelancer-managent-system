import axiosClient from './axiosClient';

const authService = {
  login: async (email, senha) => {
    // Mock authentication for testing without backend
    if (email === 'test@test.com' && senha === '123456') {
      return {
        token: 'mock-jwt-token-for-testing',
        tipo: 'Bearer',
        pessoa: {
          id: 1,
          email: 'test@test.com',
          tipo: 'ADMIN',
          ativo: true
        }
      };
    }
    
    // Real backend call
    const response = await axiosClient.post('/api/auth/login', { email, senha });
    return response.data;
  },
};

export default authService;
