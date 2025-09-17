# SSE Client

A real-time Server-Sent Events (SSE) client built with Angular 20.

## Features

- **Real-time SSE Connection**: Connects to a server-sent events endpoint
- **Message Display**: Shows received messages with timestamps
- **Connection Management**: Connect/disconnect controls
- **Error Handling**: Displays connection errors and status
- **Modern UI**: Clean, responsive interface with Material Design principles

## Getting Started

### Prerequisites

- Node.js (v18 or higher)
- npm or yarn
- Angular CLI

### Installation

1. Clone the repository
2. Install dependencies:
   ```bash
   npm install
   ```

### Development

Run the development server:
```bash
npm start
```

Navigate to `http://localhost:4200/` to see the application.

### Building

Build the project for production:
```bash
npm run build
```

## Usage

1. **Start the Application**: Run `npm start` and open `http://localhost:4200/`
2. **Connect to SSE Server**: Click the "Connect" button to establish a connection to `http://localhost:8080/events`
3. **View Messages**: Real-time messages will appear in the message container with timestamps
4. **Manage Connection**: Use the Connect/Disconnect buttons to control the SSE connection
5. **Clear Messages**: Click "Clear Messages" to remove all displayed messages

## Configuration

The SSE endpoint URL is configured in the `SseService` (`src/app/sse.ts`). By default, it connects to:
```
http://localhost:8080/events
```

To change the endpoint, modify the `init()` method in the service or pass a custom URL when calling `connect()`.

## Architecture

### Components

- **AppComponent**: Main application component
- **SseDisplayComponent**: Handles SSE connection and message display

### Services

- **SseService**: Manages the EventSource connection and data flow

### Key Features

- **NgZone Integration**: Ensures proper change detection for SSE events
- **Signal-based State**: Uses Angular signals for reactive state management
- **Error Handling**: Comprehensive error handling for connection issues
- **Memory Management**: Proper cleanup of subscriptions and connections

## Testing

Run unit tests:
```bash
npm test
```

## Browser Support

This application supports all modern browsers that implement the EventSource API:
- Chrome 6+
- Firefox 6+
- Safari 5+
- Edge 12+

## Troubleshooting

### Connection Issues

1. **CORS Errors**: Ensure your SSE server allows cross-origin requests
2. **Network Issues**: Check that the server is running on the correct port
3. **Firewall**: Verify that port 8080 is accessible

### Development Issues

1. **PowerShell Execution Policy**: If you encounter PowerShell script execution errors, run:
   ```powershell
   Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
   ```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License.