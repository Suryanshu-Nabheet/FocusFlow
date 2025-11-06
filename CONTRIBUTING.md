# Contributing to FocusFlow

Thank you for your interest in contributing to FocusFlow! This document provides guidelines and instructions for contributing to the project.

## 🤝 How to Contribute

### Reporting Bugs

If you find a bug, please create an issue with:
- A clear, descriptive title
- Steps to reproduce the bug
- Expected behavior
- Actual behavior
- Screenshots (if applicable)
- Device/OS information

### Suggesting Features

We welcome feature suggestions! Please create an issue with:
- A clear description of the feature
- Use cases and benefits
- Any mockups or examples (if applicable)

### Pull Requests

1. **Fork the repository**
   ```bash
   git clone https://github.com/yourusername/FocusFlow.git
   cd FocusFlow
   ```

2. **Create a feature branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Make your changes**
   - Follow the existing code style
   - Write clear, descriptive commit messages
   - Add comments for complex logic

4. **Test your changes**
   - Ensure the app builds successfully
   - Test on different screen sizes if UI changes
   - Verify no regressions

5. **Commit your changes**
   ```bash
   git add .
   git commit -m "Add: Description of your changes"
   ```

6. **Push to your fork**
   ```bash
   git push origin feature/your-feature-name
   ```

7. **Create a Pull Request**
   - Provide a clear description of changes
   - Reference any related issues
   - Add screenshots for UI changes

## 📝 Code Style Guidelines

### Kotlin Style
- Use 4 spaces for indentation
- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Keep functions focused and small

### Compose Guidelines
- Use descriptive composable function names
- Extract reusable components
- Follow Material Design 3 guidelines
- Use proper state management

### Architecture
- Follow MVVM pattern
- Keep ViewModels focused on business logic
- Use Repository pattern for data access
- Inject dependencies using Hilt

## 🧪 Testing

- Write unit tests for ViewModels and business logic
- Test UI components when possible
- Ensure all tests pass before submitting PR

## 📋 Commit Message Format

Use clear, descriptive commit messages:
```
Add: Feature description
Fix: Bug description
Update: Change description
Refactor: Refactoring description
Docs: Documentation update
```

## ✅ Checklist

Before submitting a PR, ensure:
- [ ] Code follows the project style guidelines
- [ ] All tests pass
- [ ] No lint errors
- [ ] Code is properly commented
- [ ] README is updated if needed
- [ ] Commit messages are clear

## 🎯 Areas for Contribution

- Bug fixes
- Performance improvements
- UI/UX enhancements
- New features
- Documentation improvements
- Test coverage
- Accessibility improvements

## 📞 Questions?

If you have questions, feel free to:
- Open an issue for discussion
- Contact the maintainers

Thank you for contributing to FocusFlow! 🚀

